import NaverLogin from "../components/NaverLogin";
import KakaoLogin from "../components/KakaoLogin";
import GoogleLogin from "../components/GoogleLogin";

export default function join() {
  return (
    <>
      <div>회원가입</div>
      <NaverLogin/>
      <KakaoLogin/>
      <GoogleLogin/>
    </>
  );
}
