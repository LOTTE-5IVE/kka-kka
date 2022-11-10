import Link from "next/link";
import SearchBar from "./SearchBar";
import { useContext, useRef } from "react";
import { useEffect, useState } from "react";
import { TokenContext } from "../../context/TokenContext";
import { isLogin } from "../../hooks/isLogin";
import { getToken } from "../../hooks/getToken";
import { memberInfo } from "../../hooks/memberInfo";
import { isText } from "../../hooks/isText";
import { router } from "next/router";

export default function Header() {
  const [value, setValue] = useState("");
  const [name, setName] = useState("");
  const [login, setLogin] = useState(false);
  const { token, setToken } = useContext(TokenContext);


  const getMemberInfo = async () => {
    await memberInfo(getToken()).then((res) => {
      setName(res.name);
    });
  };

  useEffect(() => {
    if (isLogin()) {
      setToken(getToken());
      setLogin(true);
      getMemberInfo();
    }
  }, [isLogin()]);

  return (
    <div>
      <div className="HeaderWrapper">
        <div className="logo">
          <Link href="/">
            <a>
              <img src="/main/logo.png" />
            </a>
          </Link>
        </div>
        {/* <div className="searchWrapper"> */}
          {/* <div className="search"> */}
            <SearchBar value={value} setValue={setValue} />

            {/* <Link href={`/product?search=${value}`}>
              <img src="/common/main_search.png" />
            </Link> */}


          {/* </div> */}
        {/* </div> */}
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
          .HeaderWrapper {
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;

            .logo {
              position: absolute;
              transform: translate(-50%, -50%);
            }
          }
          /* .searchWrapper {
            position: absolute;
            transform: translate(-50%, 0%);
          } */
          .search {
            background: #fff;
            border: 2px solid #ed1b23;

            input[type="text"] {
              border: none;
              padding: 0;
              box-sizing: border-box;
              color: #c5c9cd;
              font-weight: 600;
            }

            input[type="text"]:focus {
              outline: none;
              color: #000;
            }

            img {
              position: relative;
            }
          }

          .icons {
            position: absolute;
            transform: translate(-50%, -50%);

            .top {
              font-weight: 700;
              display: flex;
              justify-content: space-between;
              position: relative;

              .topLeft {
                text-align: right;
              }

              .topRight {
                cursor: pointer;
              }
            }

            .bottom {
              display: flex;
              justify-content: right;
            }
          }

          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .HeaderWrapper {
              width: 1280px;
              height: 120px;

              .logo {
                left: 27%;
                top: 35%;

                img {
                  height: 95px;
                }
              }
            }

            .searchWrapper {
              left: 45%;
              top: 35%;
            }
            .search {
              border-radius: 15px;
              padding: 0 17px;

              input[type="text"] {
                border-radius: 40px;
                width: 317px;
                height: 45px;
                line-height: 45px;
                font-size: 1em;
              }

              img {
                width: 24px;
                height: 24px;
                top: 5px;
              }
            }

            .icons {
              right: 17%;
              top: 35%;
              height: 80px;
              width: 180px;

              .top {
                line-height: 40px;
                font-size: 12px;
                top: -15px;

                .topLeft {
                  width: 100px;
                }

                .topRight {
                  width: 60px;
                }
              }

              .bottom {
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

              .logo {
                left: 20vw;
                top: 3.5vw;

                img {
                  height: 5vw;
                }
              }
            }

            .search {
              left: 45vw;
              top: 3.5vw;
              border-radius: 2vw;
              padding: 0 0.9vw;

              input[type="text"] {
                border-radius: 2vw;
                width: 17vw;
                height: 2.4vw;
                line-height: 2.4vw;
                font-size: 1vw;
              }

              img {
                width: 1.3vw;
                min-width: 9px;
                /* height: 1.3vw; */
                top: 0;
              }
            }

            .icons {
              right: 10vw;
              top: 3.5vw;
              height: 4.2vw;
              width: 9.5vw;
              min-width: 110px;

              .top {
                line-height: 2.1vw;
                font-size: 0.6vw;
                top: -0.79vw;
                text-align: right;

                .topLeft {
                  width: 55%;
                }

                .topRight {
                  width: 40%;
                }
              }

              .bottom {
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

              .logo {
                left: 10%;
                top: 30%;

                img {
                  height: 60px;
                }
              }

              .search {
                left: 45%;
                top: 25%;
                border-radius: 40px;
                padding: 0 17px;

                input[type="text"] {
                  border-radius: 40px;
                  width: 120px;
                  height: 15px;
                  font-size: 0.5em;
                }

                img {
                  width: 16px;
                  height: 16px;
                  top: 2px;
                }
              }

              .icons {
                right: -18%;
                top: 30%;
                height: 80px;
                width: 180px;

                .top {
                  line-height: 40px;
                  font-size: 12px;
                  top: 0px;

                  .topLeft {
                    width: 100px;
                  }

                  .topRight {
                    width: 60px;
                    text-align: left;
                  }
                }

                .bottom {
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
