import MyInfoCard from "../../components/mypage/MyInfoCard";
import Mysidebar from "../../components/mypage/mysidebar";

export default function myCoupon() {
  return (
    <>
      <div>
        <div className="contents">
          <div className="sidebar">
            <Mysidebar />
          </div>
          <div className="wrapper">
            <div>
              <MyInfoCard />
            </div>

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
                  <th colSpan="2">사용 가능한 쿠폰</th>
                  <th colSpan="2">사용한 쿠폰</th>
                </thead>
                <tbody>
                  <tr style={{ height: "3vw" }}>
                    <td>스낵/쿠키 카테고리 쿠폰 </td>
                    <td>15% (최대 5,000원) </td>
                    <td>10,000원 이상 구매시</td>
                    <td>사용 가능 기간 2022-09-15</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <style jsx>{`
          .contents {
            margin: 0 auto;
            width: 70%;
            display: flex;
            .sidebar {
              display: inline-block;
              width: 18%;
              margin-right: 7%;
            }

            .wrapper {
              max-width: 970px;
              width: 75%;

              .myorder {
                .myorderTitle {
                  font-size: 24px;
                  font-weight: 700;
                  color: #3a3a3a;
                  border-bottom: 2px solid #3a3a3a;
                  line-height: 24px;
                  padding-bottom: 15px;
                }

                table {
                  width: 100%;
                  border-collapse: collapse;
                  font-size: 14px;
                  font-weight: 600;
                  color: #2c2c2c;
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
