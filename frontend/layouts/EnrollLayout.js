import SNSButton from "../components/member/SNSButton";

export default function EnrollLayout({ title, context }) {
  return (
    <>
      <div className="EnrollLContents">
        <div className="loginArea">
          <div className="loginTitle">
            <p>{title}</p>
          </div>
          <div className="loginSubtitle">{context}</div>
          <div className="loginButton">
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
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .EnrollLContents {
            width: 1330px;
            display: flex;
            height: 660px;
            justify-content: center;
            align-items: center;
            margin: 0 auto;

            .loginArea {
              display: block;
              text-align: center;
              height: 550px;
            }

            .loginTitle {
              min-height: 30px;
              padding: 34px 0 20px;
              line-height: 1;
              margin: 0px;

              p {
                padding: 0;
                color: #3a3a3a;
                font-size: 36px;
                font-weight: 700;
                line-height: 1;
              }
            }

            .loginSubtitle {
              color: #999999;
              font-size: 16px;
              line-height: 26px;
            }

            .loginButton {
              margin: 38px auto;
              width: 665px;
              height: 385px;
              display: flex;
              justify-content: space-between;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .EnrollLContents {
            width: 665px;
            display: flex;
            height: 1700px;
            justify-content: center;
            align-items: center;
            margin: 0 auto;

            .loginArea {
              display: block;
              text-align: center;
              height: 1500px;
            }

            .loginTitle {
              min-height: 15px;
              padding: 17px 0 10px;
              line-height: 1;
              margin: 0px;

              p {
                padding: 0;
                color: #3a3a3a;
                font-size: 72px;
                font-weight: 700;
                line-height: 1;
              }
            }

            .loginSubtitle {
              height: 200px;
              color: #999999;
              font-size: 32px;
              line-height: 60px;
            }

            .loginButton {
              margin: 19px auto;
              width: 332.5px;
              height: 800px;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .EnrollLContents {
            width: 480px;
            display: flex;
            height: 550px;
            justify-content: center;
            align-items: center;
            margin: 0 auto;

            .loginArea {
              display: block;
              text-align: center;
              height: 500px;
            }

            .loginTitle {
              min-height: 30px;
              padding: 0 0 20px;
              line-height: 1;
              margin: 0px;

              p {
                padding: 0;
                color: #3a3a3a;
                font-size: 36px;
                font-weight: 700;
                line-height: 1;
              }
            }

            .loginSubtitle {
              height: 70px;
              color: #999999;
              font-size: 16px;
              line-height: 26px;
            }

            .loginButton {
              margin: 38px auto;
              width: 480px;
              height: 200px;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            }
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
