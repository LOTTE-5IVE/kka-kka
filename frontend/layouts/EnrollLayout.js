import SNSButton from "../components/member/SNSButton";

export default function EnrollLayout({ title, context }) {
  return (
    <>
      <div className="contents">
        <div className="login-area">
          <div className="login-title">
            <h2>{title}</h2>
          </div>
          <div className="login-subtitle">{context}</div>
          <div className="login-button">
            <SNSButton
              imgsrc="/member/kakao.png"
              link={SNSUri.KAKAO.requestUri}
              context="카카오"
            />
            <SNSButton
              imgsrc="/member/naver.png"
              link={SNSUri.NAVER.requestUri}
              context="네이버"
            />
            <SNSButton
              imgsrc="/member/google.png"
              link={SNSUri.GOOGLE.requestUri}
              context="구글"
            />
          </div>
        </div>
      </div>
      <style jsx>{`
        .contents {
          width: 70%;
          margin: 0 auto;

          .login-area {
            display: block;
            text-align: center;
          }

          .login-title {
            min-height: 30px;
            padding: 34px 0 20px;
            line-height: 1;
            margin: 0px;

            h2 {
              padding: 0;
              color: #3a3a3a;
              font-size: 36px;
              font-weight: 700;
              line-height: 1;
            }
          }

          .login-subtitle {
            color: #999999;
            font-size: 16px;
            line-height: 26px;
          }

          .login-button {
            margin: 2vw auto;
            width: 50%;
            display: flex;
            height: 20vw;
            justify-content: space-between;
          }
        }
      `}</style>
    </>
  );
}

const encoding = (value) => {
  return encodeURIComponent(value);
};

const SNSLogin = {
  NAVER: {
    CID: process.env.NEXT_PUBLIC_NAVER_CLIENT_ID,
    REDIRECT_URL: encoding(process.env.NEXT_PUBLIC_NAVER_REDIRECT_URL),
    STATE: encoding(process.env.NEXT_PUBLIC_NAVER_STATE),
  },
  KAKAO: {
    CID: process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID,
    REDIRECT_URL: encoding(process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URL),
    STATE: encoding(process.env.NEXT_PUBLIC_KAKAO_STATE),
  },
  GOOGLE: {
    CID: process.env.NEXT_PUBLIC_GOOGLE_CLIENT_ID,
    REDIRECT_URL: encoding(process.env.NEXT_PUBLIC_GOOGLE_REDIRECT_URL),
    STATE: encoding(process.env.NEXT_PUBLIC_GOOGLE_STATE),
    SCOPE: encoding(process.env.NEXT_PUBLIC_GOOGLE_SCOPE),
  },
};

const SNSUri = {
  NAVER: {
    requestUri: `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${SNSLogin.NAVER.CID}&redirect_uri=${SNSLogin.NAVER.REDIRECT_URL}&state=${SNSLogin.NAVER.STATE}`,
  },
  KAKAO: {
    requestUri: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${SNSLogin.KAKAO.CID}&redirect_uri=${SNSLogin.KAKAO.REDIRECT_URL}&state=${SNSLogin.KAKAO.STATE}`,
  },
  GOOGLE: {
    requestUri: `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${SNSLogin.GOOGLE.CID}&redirect_uri=${SNSLogin.GOOGLE.REDIRECT_URL}&state=${SNSLogin.GOOGLE.STATE}&scope=${SNSLogin.GOOGLE.SCOPE}`,
  },
};
