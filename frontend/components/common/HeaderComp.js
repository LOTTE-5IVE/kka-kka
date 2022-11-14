import Link from "next/link";
import { useContext } from "react";
import { useEffect, useState } from "react";
import { TokenContext } from "../../context/TokenContext";
import { isLogin } from "../../hooks/isLogin";
import { getToken } from "../../hooks/getToken";
import { memberInfo } from "../../hooks/memberInfo";
import { isText } from "../../hooks/isText";
import { GetHApi } from "../../apis/Apis";
import { CartCntContext } from "../../context/CartCntContext";

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

  const getCartCount = async () => {
    await GetHApi("/api/members/me/carts/all", getToken()).then((res) => {
      setCartCnt(res.cartCount);
    });
  };

  function search(event) {
    if (event.key === "Enter") {
      if (value.length < 2 || value.length > 20) {
        alert("두 글자 이상 스무 글자 이하로 입력하세요.");
        return;
      }

      router.push(
        {
          pathname: `/product`,
          query: { cat_id: 0, search: value },
        },
        `/product`,
      );

      setValue("");
    }
  }
  const getCartCount = async () => {
    await GetHApi("/api/members/me/carts/all", getToken()).then((res) => {
      setCartCnt(res.cartCount);
    });
  };

  useEffect(() => {
    if (isLogin()) {
      setToken(getToken());
      setLogin(true);
      getMemberInfo();
      getCartCount();
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
        <SearchBar />
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
                  <div className="cartCntWrapper" style={{ cursor: "pointer" }}>
                    <p className="cartCnt">
                      <span>{cartCnt}</span>
                    </p>
                    <img
                      className="cart"
                      src="/common/top_cart.png"
                      style={{ cursor: "pointer" }}
                    />
                  </div>
                </Link>
              </>
            ) : (
              <>
                <Link href="/member/login">
                  <img
                    className="cart"
                    src="/common/top_mypage.png"
                    style={{ cursor: "pointer" }}
                  />
                </Link>

                <Link href="/member/login">
                  <div className="cartCntWrapper" style={{ cursor: "pointer" }}>
                    <p className="between"></p>
                    <img
                      src="/common/top_cart.png"
                      style={{ cursor: "pointer" }}
                    />
                  </div>
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

              .cartCntWrapper {
                display: flex;
                .cartCnt {
                  position: relative;
                  top: -10%;
                  left: 70%;
                  transform: translate(-50%, -50%);
                  background: #ff3d44;
                  color: #fff;
                  width: 19px;
                  height: 19px;
                  font-size: 12px;
                  text-align: center;
                  font-weight: 700;
                  border-radius: 50%;
                }

                .between {
                  width: 19px;
                  height: 19px;
                }
              }
            }
          }

          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .HeaderWrapper {
              width: 1280px;
              height: 120px;

              .logo {
                left: 515px;
                top: 65px;

                img {
                  height: 95px;
                }
              }
            }

            .icons {
              left: 1400px;
              top: 65px;
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
                  margin-right: 15px;
                  text-align: right;
                }
              }

              .bottom {
                img {
                  width: 29px;
                  height: 29px;
                  margin-right: 15px;
                }
              }
            }
          }

          @media screen and (max-width: 768px) {
            .HeaderWrapper {
              width: 67.5vw;
              height: 10vw;

              .logo {
                left: 20vw;
                top: 5vw;

                img {
                  height: 5vw;
                }
              }
            }

            .icons {
              right: 10vw;
              top: 3.5vw;
              width: 9.5vw;
              min-width: 110px;
              height: 5vw;

              .top {
                line-height: 2.1vw;
                font-size: 0.6vw;
                top: 0;
                text-align: right;

                .topLeft {
                  width: 55%;
                }

                .topRight {
                  width: 40%;
                }
              }

              .bottom {
                display: flex;
                justify-content: right;
                align-items: end;
                height: 6vw;

                img {
                  width: 4vw;
                  height: 4vw;
                  margin: 0 1.5vw;
                }

                .cartCntWrapper {
                  display: flex;
                  height: 60%;

                  .cartCnt {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    margin: 0;
                    position: relative;
                    top: 30%;
                    left: 77%;
                    transform: translate(-50%, -50%);
                    background: #ff3d44;
                    color: #fff;
                    width: 2.5vw;
                    height: 2.5vw;
                    font-size: 1vw;
                    text-align: center;
                    font-weight: 700;
                    border-radius: 50%;
                  }

                  .between {
                    width: 2.5vw;
                    height: 2.5vw;
                  }
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
                left: 80px;
                top: 35px;
                img {
                  height: 60px;
                }
              }

              .icons {
                left: 350px;
                top: 35px;
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
                  display: flex;
                  height: 25px;

                  img {
                    width: 25px;
                    height: 25px;
                    margin: 0px 15px;
                  }

                  .cartCntWrapper {
                    display: flex;
                    height: 100%;

                    .cartCnt {
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      position: relative;
                      top: 30%;
                      left: 70%;
                      transform: translate(-50%, -50%);
                      background: #ff3d44;
                      color: #fff;
                      width: 15px;
                      height: 15px;
                      font-size: 10px;
                      text-align: center;
                      font-weight: 700;
                      border-radius: 50%;
                    }

                    .between {
                      width: 15px;
                      height: 15px;
                    }
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
