export const getToken = () => {
  if (typeof window === "undefined") return;

  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);
  let token;

  if (obj) {
    token = obj.value;
  }

  return token;
};
