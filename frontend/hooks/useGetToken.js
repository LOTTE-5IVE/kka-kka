export const useGetToken = () => {
  const objString = localStorage.getItem("accessToken");
  const obj = JSON.parse(objString);
  let token;

  console.log("useGetToken");

  if (obj && new Date().getTime() > obj.expire) {
    localStorage.removeItem("accessToken");
    alert("세션이 만료되었습니다.");
    document.location.href = "/member/login";
  } else if (obj) {
    token = obj.value;
  }
  console.log(token);

  return token;
};
