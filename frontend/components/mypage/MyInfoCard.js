const gradeColor = {
  GOLD: "#ffd700",
  SILVER: "#C0C0C0",
  BRONZE: "#b08d57",
};

export default function MyInfoCard({ name, grade }) {
  return (
    <>
      <div className="MyInfoCard">
        <div className="myinfoLeft">
          <div style={{ padding: "15px 0" }}>
            <span className="myname">{name}</span>님
          </div>
          <div className="grade">
            <span>
              <span
                style={{
                  display: "inline-block",
                  width: "15px",
                  height: "15px",
                  borderRadius: "50%",
                  backgroundColor: gradeColor[grade],
                }}
              ></span>
              {grade}
            </span>
            {/* <span className="gradeBtn">등급 혜택</span> */}
          </div>
        </div>
        <div className="myinfoRight">
          <div>
            주문내역<div className="myOrderCnt">3</div>
          </div>
          <div>
            쿠폰<div className="myCouponCnt">3</div>
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

              .myname {
                font-size: 24px;
                vertical-align: bottom;
              }

              .grade {
                padding-top: 10px;
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

              .myname {
                font-size: 24px;
                vertical-align: bottom;
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
              margin: 10px 0px 20px 20px;
              padding: 0px 15px;
              border-right: 2px solid #e6e4e4;

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
              padding: 10px 30px;
              display: flex;
              justify-content: space-around;
              text-align: center;
              font-size: 12px;

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
      `}</style>
    </>
  );
}
