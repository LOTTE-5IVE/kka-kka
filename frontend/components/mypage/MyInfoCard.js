import axios from "axios";
import { useEffect } from "react";
import { useContext } from "react";
import { useState } from "react";
import { GetHApi } from "../../apis/Apis";
import { TokenContext } from "../../context/TokenContext";
import { getToken } from "../../hooks/getToken";
import { memberInfo } from "../../hooks/memberInfo";

export default function MyInfoCard() {
  const [name, setName] = useState("");
  const [grade, setGrade] = useState("");
  const [orderCnt, setOrderCnt] = useState(0);
  const [couponCnt, setCouponCnt] = useState(0);
  const { token, setToken } = useContext(TokenContext);

  const gradeColor = {
    GOLD: "#ffd700",
    SILVER: "#C0C0C0",
    BRONZE: "#b08d57",
  };

  const getOrderCount = async () => {
    await GetHApi("/api/members/me/orders/all", token).then((res) => {
      setOrderCnt(res.orderCount);
    });
  };

  const getCouponCount = async () => {
    await GetHApi("/api/members/me/coupons/all", token).then((res) => {
      console.log(res);
      // setCouponCnt(res.couponCount);
    });
  };

  useEffect(() => {
    memberInfo(token).then((res) => {
      if (res) {
        setName(res.name);
        setGrade(res.grade);

        getOrderCount();
        getCouponCount();
      }
    });
  }, []);

  return (
    <>
      <div className="MyInfoCard">
        <div className="myinfoLeft">
          <div className="nameWrapper">
            <span className="myname">{name}</span>님
          </div>
          <div className="grade">
            <span>
              <span
                className="gradeMedal"
                style={{ backgroundColor: `${gradeColor[grade]}` }}
              ></span>
              {grade}
            </span>
          </div>
        </div>
        <div className="myinfoRight">
          <div>
            주문내역<div className="myOrderCnt">{orderCnt}</div>
          </div>
          <div>
            쿠폰<div className="myCouponCnt">{couponCnt}</div>
          </div>
        </div>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .MyInfoCard {
            color: #3a3a3a;
            font-weight: 600;
            line-height: 1;
            height: 135px;
            margin: 48px 0 97px;
            display: flex;
            border: 2px solid #e6e4e4;
            border-radius: 25px;

            .myinfoLeft {
              width: 400px;
              margin: 20px 0px 20px 75px;
              padding: 0px 15px;
              border-right: 2px solid #e6e4e4;

              .nameWrapper {
                padding: 15px 0;
                .myname {
                  font-size: 24px;
                  vertical-align: bottom;
                }
              }

              .grade {
                padding-top: 10px;

                .gradeMedal {
                  display: inline-block;
                  width: 15px;
                  height: 15px;
                  border-radius: 50%;
                }
              }

              .gradeBtn {
                margin-left: 20px;
                font-size: 12px;
                font-weight: 900;
                padding: 3px 5px;
                background-color: #dedede;
                border-radius: 5px;
              }
            }

            .myinfoRight {
              width: 467px;
              margin: 20px;
              padding: 20px 50px;
              display: flex;
              justify-content: space-around;
              text-align: center;

              .myOrderCnt {
                font-weight: 900;
                padding: 20px 0;
              }

              .myCouponCnt {
                color: red;
                font-weight: 900;
                padding: 20px 0;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .MyInfoCard {
            color: #3a3a3a;
            font-weight: 600;
            line-height: 1;
            height: 7.1vw;
            min-height: 100px;
            margin: 2.53vw 0 5.1vw;
            display: flex;
            border: 2px solid #e6e4e4;
            border-radius: 25px;

            .myinfoLeft {
              width: 27vw;
              margin: auto;
              border-right: 2px solid #e6e4e4;
              font-size: 1vw;
              display: flex;
              flex-direction: column;
              align-items: center;

              .nameWrapper {
                padding-bottom: 15px;
                .myname {
                  padding-bottom: 15px;
                  font-size: 3vw;
                  vertical-align: bottom;
                }
              }
              .grade {
                padding-top: 0;
                font-size: 0.63vw;

                .gradeMedal {
                  display: inline-block;
                  width: 1.5vw;
                  height: 1.5vw;
                  border-radius: 50%;
                  background-color: ${gradeColor[grade]};
                }
              }

              .gradeBtn {
                margin-left: 20px;
                font-size: 0.63vw;
                font-weight: 900;
                padding: 3px 5px;
                background-color: #dedede;
                border-radius: 5px;
              }
            }

            .myinfoRight {
              width: 33vw;
              margin: 1.05vw;
              display: flex;
              justify-content: space-around;
              text-align: center;
              align-items: center;

              .myOrderCnt {
                font-weight: 900;
                padding: 1.05vw 0;
              }

              .myCouponCnt {
                color: red;
                font-weight: 900;
                padding: 1.05vw 0;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .MyInfoCard {
            color: #3a3a3a;
            font-weight: 600;
            line-height: 1;
            height: 100px;
            margin: 10px 0 30px;
            display: flex;
            border: 2px solid #e6e4e4;
            border-radius: 25px;

            .myinfoLeft {
              width: 150px;
              margin: auto;
              border-right: 2px solid #e6e4e4;
              display: flex;
              flex-direction: column;
              align-items: center;

              .myname {
                font-size: 16px;
                vertical-align: bottom;
              }

              .grade {
                /* padding-top: 10px;  */
              }

              .gradeBtn {
                margin-left: 20px;
                font-size: 12px;
                font-weight: 900;
                padding: 3px 5px;
                background-color: #dedede;
                border-radius: 5px;
              }
            }

            .myinfoRight {
              width: 190px;
              margin: 15px 10px;
              display: flex;
              justify-content: space-around;
              text-align: center;
              align-items: center;
              font-size: 12px;

              .myOrderCnt,
              .myCouponCnt {
                font-weight: 900;
                padding: 10px 0;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
