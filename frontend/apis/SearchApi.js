import axios from "axios";

export const fetchSearchData = (sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie) => {
  const pPromise = getProduct(sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie);
  return {
    productList: wrapPromise(pPromise),
  };
};

const getProduct = (sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie) => {
  return axios
  .get(`/api/es/search?sortby=${sort}&page=${page}&keyword=${keyword}&catecodes=${category}&minprice=${minPrice}&maxprice=${maxPrice}&mincalorie=${minCalorie}&maxcalorie=${maxCalorie}`)
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