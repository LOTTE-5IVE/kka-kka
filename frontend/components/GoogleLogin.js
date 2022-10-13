export default function GoogleLogin() {
  const encoding = (value) => {
    return encodeURIComponent(value);
  }

  const CID = process.env.NEXT_PUBLIC_GOOGLE_CLIENT_ID;
  const REDIRECT_URL = encoding(process.env.NEXT_PUBLIC_GOOGLE_REDIRECT_URL);
  const STATE = encoding(process.env.NEXT_PUBLIC_GOOGLE_STATE);
  const SCOPE = encoding(process.env.NEXT_PUBLIC_GOOGLE_SCOPE)

  const requestUri = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${CID}&redirect_uri=${REDIRECT_URL}&state=${STATE}&scope=${SCOPE}`
  return (
      <a href={requestUri}>
        <button>
          구글 로그인
        </button>
      </a>
  )
};