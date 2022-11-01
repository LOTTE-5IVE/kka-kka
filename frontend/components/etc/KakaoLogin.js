export default function KakaoLogin() {
  const encoding = (value) => {
    return encodeURIComponent(value);
  }

  const CID = process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID;
  const REDIRECT_URL = encoding(process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URL);
  const STATE = encoding(process.env.NEXT_PUBLIC_KAKAO_STATE);

  const requestUri = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${CID}&redirect_uri=${REDIRECT_URL}&state=${STATE}`;

  return (
      <a href={requestUri}>
        <button>
          카카오 로그인
        </button>
      </a>
  )
}