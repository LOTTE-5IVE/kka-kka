import { useState } from "react";

export const useGetToken = () => {
  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);
  let token;

  console.log("useGetToken");

  if (obj && new Date().getTime() > obj.expire) {
    localStorage.removeItem("accessToken");
    token = "";
  } else if (obj) {
    token = obj.value + "";
  }
  console.log(token);

  return token;
};
