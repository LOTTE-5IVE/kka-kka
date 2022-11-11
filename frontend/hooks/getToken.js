export const getToken = () => {
  if (typeof window === "undefined") return;

  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);
  let token;

  if (obj && new Date().getTime() > obj.expire) {
    localStorage.removeItem("accessToken");
    alert("세션이 만료되었습니다.");
    document.location.href = "/member/login";
  } else if (obj) {
    token = obj.value;
  }

  return token;
};
