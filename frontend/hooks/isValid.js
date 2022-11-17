export const isValid = () => {
  if (typeof window === "undefined") return;

  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);

  if (obj) {
    return true;
  }

  return false;
};
