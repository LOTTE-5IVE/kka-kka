import { useState } from "react";
import Title from "../../components/common/Title";
import MyInfoCard from "../../components/mypage/MyInfoCard";
import Mysidebar from "../../components/mypage/mysidebar";

export default function MyOrder() {
  const items = [
    "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "g",
    "h",
    "i",
    "j",
    "k",
    "l",
    "m",
    "n",
  ];
  const [marker, setMarker] = useState(5);

  function loadList() {
    setMarker(marker + 5);
  }

  const list = items.map((item) => <li>{item}</li>);

  return (
    <>
      <Title title="주문내역" />
      <div>
        <div className="wrapper">
          <div className="App">
            <ul>{list.slice(0, marker)}</ul>
            {items.length > marker ? (
              <button onClick={loadList}>MORE</button>
            ) : (
              ""
            )}
          </div>
          <div className="myorder">
            <div className="myorderTitle">주문내역</div>
            <table>
              <colgroup>
                <col style={{ width: "15%" }} />
                <col style={{ width: "70%" }} />
                <col style={{ width: "15%", textAlign: "right" }} />
              </colgroup>
              <tbody>
                <tr style={{ height: "3vw" }}>
                  <td
                    colSpan="2"
                    style={{
                      fontSize: "16px",
                      fontWeight: "600",
                      color: "#2c2c2c",
                    }}
                  >
                    주문번호 123456
                  </td>

                  <td>2022-xx-xx</td>
                </tr>
                <tr style={{ height: "1vw" }}>
                  <td rowSpan="3">
                    <img width="96px" src="/sample.png" />
                  </td>
                  <td>상품이름</td>
                  <td style={{ color: "#898989" }}>10,000원</td>
                </tr>
                <tr style={{ height: "1vw" }}>
                  <td style={{ color: "#898989" }}>상품상세옵션</td>
                  <td
                    style={{
                      fontSize: "18px",
                      fontWeight: "600",
                    }}
                  >
                    8,000원
                  </td>
                </tr>
                <tr
                  style={{ height: "2vw", borderBottom: "2px solid #d0cfcf" }}
                >
                  <td></td>
                  <td>x 1</td>
                </tr>
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

                tr:nth-child(4n + 2) td:nth-child(3) {
                  text-align: right;
                  text-decoration: line-through;
                  padding-right: 20px;
                }

                tr:not(:nth-child(4n + 2)) td:nth-child(2) {
                  text-align: right;
                  padding-right: 20px;
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
