import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { GetHApi } from "../../apis/Apis";
import Title from "../../components/common/Title";
import { getToken } from "../../hooks/getToken";
import MyCouponCard from "./MyCouponCard";

export default function MyCoupon() {
  const [tab, setTab] = useState("valid");
  const [moreToggle, setMoreToggle] = useState(true);
  const [token, setToken] = useState("");
  const [coupons, setCoupons] = useState();

  const getAvailableCoupon = async () => {
    await GetHApi("/api/coupons/me/available", token).then((res) => {
      setCoupons(res);
    });
  };

  const getUnavailableCoupon = async () => {
    await GetHApi("/api/coupons/me/unavailable", token).then((res) => {
      setCoupons(res);
    });
  };

  useEffect(() => {
    setToken(getToken());
    if (token !== "") {
      getAvailableCoupon();
    }
  }, [token]);

  return (
    <>
      <Title title="쿠폰함" />
      <div>
        <div className="MyCouponWrapper">
          <div className="myorder">
            <div className="myorderTitle">쿠폰</div>
            <table>
              <colgroup>
                <col style={{ width: "30%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "25%" }} />
                <col style={{ width: "10%" }} />
              </colgroup>
              <thead>
                <tr>
                  <th
                    className={`${tab === "valid" ? "left" : "normalLeft"}`}
                    onClick={() => {
                      setTab("valid");
                      getAvailableCoupon();
                    }}
                    colSpan="2"
                  >
                    사용 가능한 쿠폰
                  </th>
                  <th
                    className={`${tab === "invalid" ? "right" : "normalRight"}`}
                    onClick={() => {
                      setTab("invalid");
                      getUnavailableCoupon();
                    }}
                    colSpan="3"
                  >
                    사용한 쿠폰
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>쿠폰명</td>
                  <td>할인율</td>
                  <td>최소 주문금액</td>
                  <td>사용 가능 기간</td>
                  <td>분류</td>
                </tr>
                {tab == "valid" ? (
                  <MyCouponCard coupons={coupons} />
                ) : (
                  <MyCouponCard coupons={coupons} />
                )}
                <tr className="loadMore">
                  <td colSpan="5">
                    {coupons?.length > 0 && moreToggle ? (
                      <div
                        onClick={() => {
                          console.log("loadMore clicked", coupons);
                        }}
                        className={"d-flex align-center moreBtn"}
                        style={{ cursor: "pointer" }}
                      >
                        <span>▼ 더보기</span>
                      </div>
                    ) : (
                      <div className="couponEmpty">
                        <img
                          className="couponEmptyImg"
                          src="/member/no_item.gif"
                        />
                        <span className="couponEmptyComment">
                          쿠폰 내역이 없습니다.
                        </span>
                      </div>
                    )}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <style jsx>{`
          .d-flex {
            display: flex;
            justify-content: center;
          }

          .align-center {
            align-items: center;
          }

          .moreBtn {
            margin-bottom: 2rem;
            padding: 1rem;
            border: 1px solid #c5c5c5;
            color: #525252;
          }

          .couponEmpty {
            display: flex;
            flex-direction: column;

            text-align: center;
            .couponEmptyComment {
              color: #9a9a9a;
            }
          }

          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .MyCouponWrapper {
              width: 970px;

              .myorder {
                .myorderTitle {
                  font-size: 24px;
                  font-weight: 700;
                  color: #3a3a3a;
                  line-height: 24px;
                  padding-bottom: 15px;
                }

                table {
                  width: 970px;
                  border-collapse: collapse;
                  font-size: 14px;
                  font-weight: 600;
                  text-align: center;
                  color: #2c2c2c;

                  thead {
                    tr {
                      height: 36px;
                    }
                  }

                  tr {
                    height: 40px;
                  }

                  .normalLeft {
                    border: 2px solid #e6e4e4;
                    border-right: 2px solid;
                    border-bottom: 2px solid;
                    cursor: pointer;
                  }

                  .normalRight {
                    border: 2px solid #e6e4e4;
                    border-bottom: 2px solid;
                    cursor: pointer;
                  }

                  .left {
                    color: #000;
                    border: 2px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .right {
                    color: #000;
                    border: 2px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .couponEmpty {
                    .couponEmptyImg {
                      margin: 70px auto 30px;
                      width: 70px;
                    }
                    .couponEmptyComment {
                      margin-bottom: 70px;
                      font-size: 18px;
                      line-height: 1;
                    }
                  }
                }
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .MyCouponWrapper {
              width: 67vw;

              .myorder {
                .myorderTitle {
                  font-size: 4vw;
                  font-weight: 700;
                  color: #3a3a3a;
                  line-height: 24px;
                  padding-bottom: 15px;
                }

                table {
                  width: 67vw;
                  border-collapse: collapse;
                  font-size: 2vw;
                  font-weight: 600;
                  text-align: center;
                  color: #2c2c2c;

                  thead {
                    tr {
                      height: 36px;
                    }
                  }

                  tr {
                    height: 40px;
                  }

                  .normalLeft {
                    border: 2px solid #e6e4e4;
                    border-right: 2px solid;
                    border-bottom: 2px solid;
                    cursor: pointer;
                  }

                  .normalRight {
                    border: 2px solid #e6e4e4;
                    border-bottom: 2px solid;
                    cursor: pointer;
                  }

                  .left {
                    color: #000;
                    border: 2px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .right {
                    color: #000;
                    border: 2px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .moreBtn {
                    margin-bottom: 2rem;
                    padding: 0.5rem;
                    border: 1px solid #c5c5c5;
                    color: #525252;
                  }

                  .couponEmpty {
                    .couponEmptyImg {
                      margin: 20px auto;
                      width: 40px;
                    }
                    .couponEmptyComment {
                      margin-bottom: 70px;
                      font-size: 12px;
                      line-height: 1;
                    }
                  }
                }
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .MyCouponWrapper {
              width: 330px;

              .myorder {
                .myorderTitle {
                  font-size: 16px;
                  font-weight: 700;
                  color: #3a3a3a;
                  line-height: 24px;
                  padding-bottom: 15px;
                }

                table {
                  width: 330px;
                  border-collapse: collapse;
                  font-size: 10px;
                  font-weight: 600;
                  text-align: center;
                  color: #2c2c2c;

                  thead {
                    tr {
                      height: 20px;
                    }
                  }

                  tr {
                    height: 24px;
                  }

                  .normalLeft {
                    border: 1px solid #e6e4e4;
                    border-right: 1px solid;
                    border-bottom: 1px solid;
                    cursor: pointer;
                  }

                  .normalRight {
                    border: 1px solid #e6e4e4;
                    border-bottom: 1px solid;
                    cursor: pointer;
                  }

                  .left {
                    color: #000;
                    border: 1px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .right {
                    color: #000;
                    border: 1px solid;
                    border-bottom: none;
                    cursor: pointer;
                  }

                  .moreBtn {
                    margin-bottom: 1rem;
                    padding: 0.5rem;
                    border: 1px solid #c5c5c5;
                    color: #525252;

                    span {
                      font-size: 12px;
                    }
                  }

                  .couponEmpty {
                    .couponEmptyImg {
                      margin: 20px auto;
                      width: 40px;
                    }
                    .couponEmptyComment {
                      margin-bottom: 70px;
                      font-size: 12px;
                      line-height: 1;
                    }
                  }
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
