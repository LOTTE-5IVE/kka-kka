import NaverLogin from "../components/NaverLogin";
import KakaoLogin from "../components/KakaoLogin";

export default function join() {
  return (
    <>
      <div>회원가입</div>
      <NaverLogin/>
      <KakaoLogin/>
    </>
  );
}
