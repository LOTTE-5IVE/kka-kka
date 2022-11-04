export const isValid = () => {
  if (typeof window === "undefined") return;

  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);

  if (obj && new Date().getTime() > obj.expire) {
    localStorage.removeItem("accessToken");
    alert("세션이 만료되었습니다.");
    document.location.href = "/member/login";
    return false;
  }

  if (obj && new Date().getTime() < obj.expire) {
    return true;
  }

  return false;
};
