export default function MyInfoCard() {
  return (
    <>
      <div className="myinfo">
        <div className="myinfo_left">
          <div style={{ padding: "15px 0" }}>
            <span className="myname">익명의 회원</span>님
          </div>
          <div style={{ paddingTop: "10px" }}>
            <span>
              <span
                style={{
                  display: "inline-block",
                  width: "15px",
                  height: "15px",
                  borderRadius: "50%",
                  backgroundColor: "red",
                }}
              ></span>
              Bronze
            </span>
            <span className="gradeBtn">등급 혜택</span>
          </div>
        </div>
        <div className="myinfo_right">
          <div>
            주문내역<div className="myOrderCnt">3</div>
          </div>
          <div>
            쿠폰<div className="myCouponCnt">3</div>
          </div>
        </div>
      </div>

      <style jsx>{`
        .myinfo {
          color: #3a3a3a;
          font-weight: 600;
          line-height: 1;
          height: 7vw;
          margin: 5% 0 10%;
          display: flex;
          border: 2px solid #e6e4e4;
          border-radius: 25px;

          .myinfo_left {
            width: 45%;
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

          .myinfo_right {
            width: 50%;

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
      `}</style>
    </>
  );
}
