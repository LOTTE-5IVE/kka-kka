export default function NaverLogin() {
  const encoding = (value) => {
    return encodeURIComponent(value);
  }

  const CID = process.env.NEXT_PUBLIC_NAVER_CLIENT_ID;
  const REDIRECT_URL = encoding(process.env.NEXT_PUBLIC_NAVER_REDIRECT_URL);
  const STATE = encoding(process.env.NEXT_PUBLIC_NAVER_STATE);

  const requestUri = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${CID}&redirect_uri=${REDIRECT_URL}&state=${STATE}`;

  return (
      <a href={requestUri}>
        <button>
          네이버 로그인
        </button>
      </a>
  )
}