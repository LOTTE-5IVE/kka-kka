import Link from "next/link";
import ButtonComp from "../../components/common/buttonComp";
import Title from "../../components/common/Title";
import SNSButton from "../../components/member/SNSButton";

export default function cart() {
  return (
    <>
      <Title title="장바구니" />
      <div className="title">
        <h2>장바구니</h2>
      </div>
      <div className="contents">
        <div className="orderTables">
          <div className="orderList">
            <table>
              <colgroup>
                <col style={{ width: "1vw" }} />
                <col style={{ width: "8vw" }} />
                <col style={{ width: "27vw" }} />
                <col style={{ width: "10vw" }} />
                <col style={{ width: "8vw" }} />
                <col style={{ width: "8vw" }} />
                <col style={{ width: "2vw" }} />
              </colgroup>
              <thead>
                <tr style={{ height: "3vw" }}>
                  <th scope="col">
                    <input type="checkbox" onClick="#" />
                  </th>
                  <th scope="col">이미지</th>
                  <th scope="col">상품정보</th>
                  <th scope="col">수량</th>
                  <th scope="col">배송비</th>
                  <th scope="col">합계</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr style={{ height: "6vw" }}>
                  <td>
                    <input type="checkbox" onClick="#" />
                  </td>
                  <td>
                    <img src="/sample.png" />
                  </td>
                  <td>몽쉘 생크림 오리지널 192g</td>
                  <td>수량</td>
                  <td>배송비</td>
                  <td>합계</td>
                  <td>
                    <img width="20px" src="/cancelred.png" />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div className="orderSummary">
            <table>
              <colgroup>
                <col style={{ width: "14vw" }} />
                <col style={{ width: "11vw" }} />
                <col style={{ width: "14vw" }} />
                <col style={{ width: "12vw" }} />
                <col style={{ width: "14vw" }} />
              </colgroup>
              <thead>
                <tr style={{ height: "3vw" }}>
                  <th scope="col">총 상품금액</th>
                  <th scope="col"></th>
                  <th scope="col">총 할인금액</th>
                  <th scope="col"></th>
                  <th scope="col">결제예정금액</th>
                </tr>
              </thead>
              <tbody>
                <tr style={{ height: "7vw" }}>
                  <td>상품금액</td>
                  <td>-</td>
                  <td>할인금액</td>
                  <td>=</td>
                  <td>결제예정금액</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div className="order-btn">
          <Link href="/payment">
            <a>
              <ButtonComp context="전체상품 주문" />
            </a>
          </Link>
          <ButtonComp context="선택상품 주문" />
        </div>
      </div>
      <style jsx>{`
        .title {
          margin-top: 5vw;
          text-align: center;
          padding: 0;
          color: #3a3a3a;
          font-size: 1.5vw;
          font-weight: 700;
          line-height: 1;
        }

        .contents {
          text-align: center;

          .orderTables {
            display: flex;
            flex-direction: column;
            align-items: center;

            .orderList {
              table {
                border-collapse: collapse;
                border-bottom: 1px solid #dfdfdf;
              }
              th {
                border: 0;
                margin: 0;
                padding: 0;

                border-spacing: 0;
                border-top: 2px solid #1a1a1a;
                border-bottom: 1px solid #dfdfdf;
              }
            }
          }

          .orderSummary {
            margin-top: 5vw;

            table {
              border-collapse: collapse;
              border-bottom: 1px solid #dfdfdf;
            }
            th {
              border: 0;
              margin: 0;
              padding: 0;

              border-spacing: 0;
              border-top: 2px solid #1a1a1a;
              border-bottom: 1px solid #dfdfdf;
            }
          }

          .order-btn {
            margin: 2vw auto 3vw;
            width: 30%;
            display: flex;
            justify-content: space-around;
          }
        }
      `}</style>
    </>
  );
}
