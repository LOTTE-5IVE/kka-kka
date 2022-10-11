import Title from "../../components/common/Title";
import SNSButton from "../../components/member/SNSButton";

export default function login() {
  return (
    <>
      <Title title="로그인" />
      <div className="contents">
        <div className="login-area">
          <div className="login-title">
            <h2>로그인</h2>
          </div>
          <div className="login-subtitle">
            SNS로 편리하게 로그인하시고
            <br />
            다양한 서비스를 이용해보세요.
          </div>
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
            <SNSButton imgsrc="/member/apple.png" link="/" context="애플" />
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
};

const SNSUri = {
  NAVER: {
    requestUri: `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${SNSLogin.NAVER.CID}&redirect_uri=${SNSLogin.NAVER.REDIRECT_URL}&state=${SNSLogin.NAVER.STATE}`,
  },
  KAKAO: {
    requestUri: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${SNSLogin.KAKAO.CID}&redirect_uri=${SNSLogin.KAKAO.REDIRECT_URL}&state=${SNSLogin.KAKAO.STATE}`,
  },
};
