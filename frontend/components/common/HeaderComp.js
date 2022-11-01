import Link from "next/link";
import { useEffect, useState } from "react";
import { isLogin } from "../../hooks/isLogin";
import { useGetToken } from "../../hooks/useGetToken";
import { useMemberInfo } from "../../hooks/useMemberInfo";
import { useTextCheck } from "../../hooks/useTextCheck";

export default function Header() {
  const [value, setValue] = useState("");
  const [name, setName] = useState("");
  const [login, setLogin] = useState(false);

  function search(event) {
    if (event.key === "Enter") {
      if (value.length < 2 || value.length > 20) {
        alert("두 글자 이상 스무 글자 이하로 입력하세요.");
        return;
      }

      window.location.href = `/product?search=${value}`;
      setValue("");
    }
  }

  useEffect(() => {
    if (!login && isLogin()) {
      setLogin(true);

      useMemberInfo(useGetToken()).then((res) => {
        setName(res.name);
      });
    }
  }, [isLogin()]);

  return (
    <div>
      <div className="HeaderWrapper">
        <div className="logo">
          <div
            onClick={() => {
              document.location.href = "/";
            }}
            style={{ cursor: "pointer" }}
          >
            <img src="/main/logo.png" />
          </div>
        </div>
        <div className="search">
          <input
            type="text"
            size="30"
            placeholder="검색어를 입력해주세요"
            onChange={(e) => {
              if (useTextCheck(e.currentTarget.value)) {
                setValue(e.currentTarget.value);
              } else {
                alert("특수문자는 입력할 수 없습니다.");
                e.currentTarget.value = "";
              }
            }}
            onKeyUp={(event) => {
              search(event, value);
            }}
          ></input>

          <Link href={`/product?search=${value}`}>
            <img src="/common/main_search.png" />
          </Link>
        </div>
        <div className="icons">
          <div className="top">
            {login ? (
              <>
                <div className="topLeft">{name}님</div>
                <div
                  className="topRight"
                  onClick={() => {
                    localStorage.removeItem("accessToken");
                    document.location.href = "/";
                  }}
                >
                  <a>로그아웃</a>
                </div>
              </>
            ) : (
              <>
                <Link href="/member/join">
                  <a className="topLeft">회원가입</a>
                </Link>

                <Link href="/member/login">
                  <a className="topRight">로그인</a>
                </Link>
              </>
            )}
          </div>
          <div className="bottom">
            {login ? (
              <>
                <Link href="/mypage">
                  <img
                    src="/common/top_mypage.png"
                    style={{ cursor: "pointer" }}
                  />
                </Link>

                <Link href="/member/cart">
                  <img
                    src="/common/top_cart.png"
                    style={{ cursor: "pointer" }}
                  />
                </Link>
              </>
            ) : (
              <>
                <Link href="/member/login">
                  <img
                    src="/common/top_mypage.png"
                    style={{ cursor: "pointer" }}
                  />
                </Link>

                <Link href="/member/login">
                  <img
                    src="/common/top_cart.png"
                    style={{ cursor: "pointer" }}
                  />
                </Link>
              </>
            )}
          </div>
        </div>
        <style jsx>{`
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .HeaderWrapper {
              width: 1280px;
              height: 120px;
              margin: 0 auto;
              display: flex;
              justify-content: space-between;
              align-items: center;

              .logo {
                position: absolute;
                left: 27%;
                top: 35%;
                transform: translate(-50%, -50%);

                img {
                  height: 95px;
                }
              }
            }

            .search {
              position: absolute;
              left: 45%;
              top: 35%;
              transform: translate(-50%, -50%);
              border: 2px solid #ed1b23;
              border-radius: 40px;
              padding: 0 17px;

              input[type="text"] {
                border: none;
                border-radius: 40px;
                width: 317px;
                height: 45px;
                line-height: 45px;
                padding: 0;
                box-sizing: border-box;
                color: #c5c9cd;
                font-size: 1em;
                font-weight: 600;
              }

              input[type="text"]:focus {
                outline: none;
                color: #000;
              }

              img {
                width: 24px;
                height: 24px;
                position: relative;
                top: 5px;
              }
            }

            .icons {
              position: absolute;
              right: 17%;
              top: 35%;
              transform: translate(-50%, -50%);
              height: 80px;
              width: 180px;

              .top {
                line-height: 40px;
                font-weight: 700;
                font-size: 12px;
                display: flex;
                justify-content: space-between;
                position: relative;
                top: -15px;

                .topLeft {
                  width: 100px;
                  text-align: right;
                }

                .topRight {
                  width: 60px;
                  cursor: pointer;
                }
              }

              .bottom {
                display: flex;
                justify-content: right;

                img {
                  width: 29px;
                  height: 29px;
                  margin: 0 15px;
                }
              }
            }
          }

          @media screen and (max-width: 768px) {
            .HeaderWrapper {
              width: 67.5vw;
              height: 6.3vw;
              margin: 0 auto;
              display: flex;
              justify-content: space-between;
              align-items: center;

              .logo {
                position: absolute;
                left: 20vw;
                top: 3.5vw;
                transform: translate(-50%, -50%);

                img {
                  height: 5vw;
                }
              }
            }

            .search {
              position: absolute;
              left: 45vw;
              top: 3.5vw;
              transform: translate(-50%, -50%);
              border: 2px solid #ed1b23;
              border-radius: 2vw;
              padding: 0 0.9vw;

              input[type="text"] {
                border: none;
                border-radius: 2vw;
                width: 17vw;
                height: 2.4vw;
                line-height: 2.4vw;
                padding: 0;
                box-sizing: border-box;
                color: #c5c9cd;
                font-size: 1vw;
                font-weight: 600;
              }

              input[type="text"]:focus {
                outline: none;
                color: #000;
              }

              img {
                width: 1.3vw;
                min-width: 9px;
                /* height: 1.3vw; */
                position: relative;
                top: 0;
              }
            }

            .icons {
              position: absolute;
              right: 10vw;
              top: 3.5vw;
              transform: translate(-50%, -50%);
              height: 4.2vw;
              width: 9.5vw;
              min-width: 110px;

              .top {
                line-height: 2.1vw;
                font-weight: 700;
                font-size: 0.6vw;
                display: flex;
                justify-content: space-between;
                position: relative;
                top: -0.79vw;
                text-align: right;

                .topLeft {
                  width: 55%;
                  text-align: right;
                }

                .topRight {
                  width: 40%;
                  cursor: pointer;
                }
              }

              .bottom {
                display: flex;
                justify-content: right;

                img {
                  width: 1.53vw;
                  margin: 0 0.79vw;
                }
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .HeaderWrapper {
              width: 480px;
              height: 70px;
              margin: 0 auto;
              display: flex;
              justify-content: space-between;
              align-items: center;

              .logo {
                position: absolute;
                left: 10%;
                top: 30%;
                transform: translate(-50%, -50%);

                img {
                  height: 60px;
                }
              }

              .search {
                position: absolute;
                left: 45%;
                top: 25%;
                transform: translate(-50%, -50%);
                border: 2px solid #ed1b23;
                border-radius: 40px;
                padding: 0 17px;

                input[type="text"] {
                  border: none;
                  border-radius: 40px;
                  width: 120px;
                  height: 15px;
                  padding: 0;
                  box-sizing: border-box;
                  color: #c5c9cd;
                  font-size: 0.5em;
                  font-weight: 600;
                }

                input[type="text"]:focus {
                  outline: none;
                  color: #000;
                }

                img {
                  width: 16px;
                  height: 16px;
                  position: relative;
                  top: 2px;
                }
              }

              .icons {
                position: absolute;
                right: -18%;
                top: 30%;
                transform: translate(-50%, -50%);
                height: 80px;
                width: 180px;

                .top {
                  line-height: 40px;
                  font-weight: 700;
                  font-size: 12px;
                  display: flex;
                  justify-content: space-between;
                  position: relative;
                  top: 0px;

                  .topLeft {
                    width: 100px;
                    text-align: right;
                  }

                  .topRight {
                    width: 60px;
                    cursor: pointer;
                    text-align: left;
                  }
                }

                .bottom {
                  display: flex;
                  justify-content: right;

                  img {
                    width: 25px;
                    height: 25px;
                    margin: 0px 20px;
                  }
                }
              }
            }
          }
        `}</style>
      </div>
    </div>
  );
}
