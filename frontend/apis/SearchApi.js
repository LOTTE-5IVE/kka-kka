import axios from "axios";

export const fetchSearchData = (sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie, cat_id) => {
  const pPromise = getProduct(sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie, cat_id);
  return {
    productList: wrapPromise(pPromise),
  };
};

const getProduct = (sort, page, keyword, category, minPrice, maxPrice, minCalorie, maxCalorie, cat_id) => {
  if(keyword){
    return axios
    .get(`/api/products?sortBy=${sort}&page=${page}&keyword=${keyword}&catecodes=${category}&minprice=${minPrice}&maxprice=${maxPrice}&mincalorie=${minCalorie}&maxcalorie=${maxCalorie}`)
    .then((res) => res.data)
    .catch((err) => console.log(err));
  }else{
    return axios
    .get(`/api/products?sortBy=${sort}&page=${page}&category=${cat_id}`)
    .then((res) => res.data)
    .catch((err) => console.log(err));
  }
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