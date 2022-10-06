import NaverLogin from "../components/NaverLogin";
import KakaoLogin from "../components/KakaoLogin";
import { useRouter } from "next/router";
import Link from "next/link";

export default function join() {
  const encoding = (value) => {
    return encodeURIComponent(value);
  };

  const CID = process.env.NEXT_PUBLIC_NAVER_CLIENT_ID;
  const REDIRECT_URL = encoding(process.env.NEXT_PUBLIC_NAVER_REDIRECT_URL);
  const STATE = encoding(process.env.NEXT_PUBLIC_NAVER_STATE);

  const requestUri = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${CID}&redirect_uri=${REDIRECT_URL}&state=${STATE}`;

  const router = useRouter();
  const category = (cat_id) => {
    router.push({
      pathname: `/product`,
      query: {
        cat_id,
      },
    });
  };

  return (
    <>
      <div>회원가입</div>
      <Link href={requestUri}>
        <a>네이버</a>
      </Link>
      <Link href={requestUri}>
        <a>카카오</a>
      </Link>
      <NaverLogin />
      <KakaoLogin />
    </>
  );
}
