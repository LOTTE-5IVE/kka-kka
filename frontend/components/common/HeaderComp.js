import Link from "next/link";
import { useEffect, useState } from "react";
import { isLogin } from "../../hooks/isLogin";
import { useGetToken } from "../../hooks/useGetToken";
import { useMemberInfo } from "../../hooks/useMemberInfo";

export default function Header() {
  const [value, setValue] = useState("");
  const [token, setToken] = useState("");
  const [name, setName] = useState("");
  const [grade, setGrade] = useState("");

  function search(event) {
    if (event.key === "Enter") {
      window.location.href = `/product?search=${value}`;
      setValue("");
    }
  }

  useEffect(() => {
    setToken(useGetToken());

    if (token !== "") {
      useMemberInfo(token).then((res) => {
        if (res) {
          setName(res.name);
          setGrade(res.grade);
        }
      });
    }
  }, [token]);

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
              setValue(e.currentTarget.value);
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
            {token ? (
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
            {token ? (
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
              width: 640px;
              height: 60px;
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
                  height: 47.5px;
                }
              }
            }

            .search {
              position: absolute;
              left: 45%;
              top: 35%;
              transform: translate(-50%, -50%);
              border: 2px solid #ed1b23;
              border-radius: 20px;
              padding: 0 8.5px;

              input[type="text"] {
                border: none;
                border-radius: 20px;
                width: 158.5px;
                height: 22.5px;
                line-height: 22.5px;
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
                width: 12px;
                height: 12px;
                position: relative;
                top: 2.5px;
              }
            }

            .icons {
              position: absolute;
              right: 17%;
              top: 35%;
              transform: translate(-50%, -50%);
              height: 40px;
              width: 90px;

              .top {
                line-height: 20px;
                font-weight: 700;
                font-size: 6px;
                display: flex;
                justify-content: space-between;
                position: relative;
                top: -7.5px;

                .topLeft {
                  width: 50px;
                  text-align: right;
                }

                .topRight {
                  width: 30px;
                  cursor: pointer;
                }
              }

              .bottom {
                display: flex;
                justify-content: right;

                img {
                  width: 14.5px;
                  height: 14.5px;
                  margin: 0 7.5px;
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
