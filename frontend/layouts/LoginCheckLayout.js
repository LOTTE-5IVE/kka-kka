import SNSButton from "../components/member/SNSButton";
import { ThemeBlue, ThemeRed } from "../typings/ThemeColor";

export default function LoginCheckLayout({ loginFlag }) {
  return (
    <>
      <div className="contents">
        <div className="login-area">
          <div className="login-title">
            {loginFlag ? (
              <img width="100px" src="/success.png" />
            ) : (
              <img width="100px" src="/fail.png" />
            )}
          </div>
          <div className="login-subtitle">
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
          {loginFlag ? (
            <div
              className="login-button"
              onClick={() => {
                document.location.href = "/";
              }}
            >
              메인 페이지로 이동
            </div>
          ) : (
            <div
              className="login-button"
              onClick={() => {
                document.location.href = "/member/login";
              }}
            >
              로그인 페이지로 이동
            </div>
          )}
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
          }

          .login-subtitle {
            color: #999999;
            font-size: 16px;
            line-height: 26px;
          }

          .login-button {
            margin: 50px auto;
            width: 250px;
            height: 80px;
            color: #fff;
            font-size: 20px;
            background-color: ${ThemeRed};
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            transition: 0.7s;
          }

          .login-button:hover {
            color: ${ThemeRed};
            background-color: #fff;
            border: 1px solid ${ThemeRed};
          }
        }
      `}</style>
    </>
  );
}