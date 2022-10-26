import { useState } from "react";
import Title from "../../components/common/Title";
import MyCouponCard from "./MyCouponCard";

export default function MyCoupon() {
  const [tab, setTab] = useState("valid");

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
                    onClick={() => setTab("valid")}
                    colSpan="2"
                  >
                    사용 가능한 쿠폰
                  </th>
                  <th
                    className={`${tab === "invalid" ? "right" : "normalRight"}`}
                    onClick={() => setTab("invalid")}
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
                  <MyCouponCard test="사용 가능한" />
                ) : (
                  <MyCouponCard test="사용한" />
                )}
                <tr className="loadMore">
                  <td
                    colSpan="5"
                    onClick={() => {
                      console.log("loadMore clicked");
                    }}
                  >
                    ▼ 더보기
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <style jsx>{`
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

                  .loadMore {
                    text-align: center;
                    border: 1px solid;
                    cursor: pointer;
                  }
                }
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
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

                  .loadMore {
                    text-align: center;
                    border: 1px solid;
                    cursor: pointer;
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

                  .loadMore {
                    text-align: center;
                    border: 1px solid;
                    cursor: pointer;
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
