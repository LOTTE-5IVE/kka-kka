import axios from "axios";

export const fetchData = (cat_id, page) => {
  const pPromise = getProduct(cat_id, page);
  return {
    productList: wrapPromise(pPromise),
  };
};

const getProduct = (cat_id, page) => {
  return axios
    .get(`/api/products?category=${cat_id}&page=${page}`)
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
