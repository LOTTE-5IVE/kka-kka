import { useState } from "react";
import Title from "../../components/common/Title";
import MyInfoCard from "../../components/mypage/MyInfoCard";
import Mysidebar from "../../components/mypage/mysidebar";
import MyCouponCard from "./MyCouponCard";

export default function MyCoupon() {
  const [tab, setTab] = useState("valid");

  return (
    <>
      <Title title="쿠폰함" />
      <div>
        <div className="wrapper">
          <div className="myorder">
            <div className="myorderTitle">쿠폰</div>
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "30%" }} />
                <col style={{ width: "30%" }} />
                <col style={{ width: "20%" }} />
              </colgroup>
              <thead>
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
                  colSpan="2"
                >
                  사용한 쿠폰
                </th>
              </thead>
              <tbody>
                {tab == "valid" ? (
                  <MyCouponCard test="사용 가능한" />
                ) : (
                  <MyCouponCard test="사용한" />
                )}
              </tbody>
            </table>
          </div>
        </div>

        <style jsx>{`
          .wrapper {
            max-width: 970px;
            width: 100%;

            .myorder {
              .myorderTitle {
                font-size: 24px;
                font-weight: 700;
                color: #3a3a3a;
                /* border-bottom: 2px solid #3a3a3a; */
                line-height: 24px;
                padding-bottom: 15px;
              }

              table {
                width: 100%;
                border-collapse: collapse;
                font-size: 14px;
                font-weight: 600;
                color: #2c2c2c;

                .normalLeft {
                  border: 2px solid #e6e4e4;
                  border-right: 2px solid;
                  border-bottom: 2px solid;
                }

                .normalRight {
                  border: 2px solid #e6e4e4;
                  border-bottom: 2px solid;
                }

                .left {
                  color: #000;
                  border: 2px solid;
                  border-bottom: none;
                }

                .right {
                  color: #000;
                  border: 2px solid;
                  border-bottom: none;
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
