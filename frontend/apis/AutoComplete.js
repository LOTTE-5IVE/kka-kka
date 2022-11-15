import axios from "axios";

export const fetchAutoData = (keyword) => {
    const pPromise = getProductNames(keyword);
    return {
      ProductNames: wrapPromise(pPromise),
    };
  };

  const getProductNames = (keyword) => {
    return axios
    .get(`/api/es/auto?keyword=${keyword}`)
    .then((res) => res.data)
    .catch((err) => console.log(err));
  };
  
  const wrapPromise = (promise) => {
    let status = "pending";
    let result;
    let suspender = promise.then(
      (res) => {
        status = "success";
        result = res;
      },
      (err) => {
        status = "error";
        result = err;
      },
    );
  
    return {
      read() {
        if (status === "pending") {
          throw suspender;
        } else if (status === "error") {
          throw result;
        } else if (status === "success") {
          return result;
        }
      },
    };
  };