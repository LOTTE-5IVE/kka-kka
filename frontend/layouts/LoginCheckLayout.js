import { useEffect } from "react";

export default function LoginCheckLayout({ loginFlag }) {
  useEffect(() => {
    if (loginFlag) {
      document.location.href = "/";
    } else {
      document.location.href = "/member/login";
    }
  }, []);

  return (
    <>
      <div className="LoginCheckLContents">
        <div className="loginArea">
          <div className="loginTitle">
            {loginFlag ? (
              <img width="100px" src="/member/success.png" />
            ) : (
              <img width="100px" src="/member/fail.png" />
            )}
          </div>
          <div className="loginSubtitle">
            {loginFlag ? (
              <>
                로그인 성공 <br />
                <br />
                KKA-KKA와 함께 행복한 쇼핑되세요.
              </>
            ) : (
              <>
                로그인 실패 <br />
                <br />
                아이디 혹은 비밀번호를 확인해주세요.
              </>
            )}
          </div>
        </div>
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .LoginCheckLContents {
            .loginArea {
              position: absolute;
              left: 951px;
              top: 470px;
              transform: translate(-50%, -50%);
              display: block;
              text-align: center;

              .loginTitle {
                min-height: 30px;
                padding: 34px 0 20px;
                line-height: 1;
                margin: 0px;
              }

              .loginSubtitle {
                color: #999999;
                font-size: 16px;
                line-height: 26px;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .LoginCheckLContents {
            .loginArea {
              position: absolute;
              left: 951px;
              top: 470px;
              transform: translate(-50%, -50%);
              display: block;
              text-align: center;

              .loginTitle {
                min-height: 30px;
                padding: 34px 0 20px;
                line-height: 1;
                margin: 0px;
              }

              .loginSubtitle {
                color: #999999;
                font-size: 16px;
                line-height: 26px;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .LoginCheckLContents {
            width: 480px;
            height: 500px;

            .loginArea {
              width: 280px;

              position: absolute;
              left: 240px;
              top: 350px;
              transform: translate(-50%, -50%);
              display: block;
              text-align: center;

              .loginTitle {
                min-height: 30px;
                padding: 34px 0 20px;
                line-height: 1;
                margin: 0px;
              }

              .loginSubtitle {
                color: #999999;
                font-size: 16px;
                line-height: 26px;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
