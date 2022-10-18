import { useState } from "react";
import Title from "../../components/common/Title";
import { useMoney } from "../../hooks/useMoney";

export default function MyOrder({ orderList }) {
  const [marker, setMarker] = useState(1);

  function loadList() {
    setMarker(marker + 1);
  }

  const olist = orderList.map((order) => (
    <>
      <tr style={{ height: "3vw" }}>
        <td
          colSpan="2"
          style={{
            fontSize: "16px",
            fontWeight: "600",
            color: "#2c2c2c",
          }}
        >
          주문번호 {order.id}
        </td>

        <td style={{ textAlign: "right", paddingRight: "15px" }}>
          {order.orderedAt}
        </td>
      </tr>
      {order.productOrders.map((product) => (
        <>
          <tr style={{ height: "1vw" }}>
            <td rowSpan="3">
              <img width="96px" src={product.product.imageUrl} />
            </td>
            <td>{product.product.name}</td>
            <td
              style={{
                color: "#898989",
                textAlign: "right",
                paddingRight: "15px",
              }}
            >
              {useMoney(product.product.price)}원
            </td>
          </tr>
          <tr style={{ height: "1vw" }}>
            <td style={{ color: "#898989" }}>상품상세옵션</td>
            <td
              style={{
                fontSize: "18px",
                fontWeight: "600",
                textAlign: "right",
                paddingRight: "15px",
              }}
            >
              {useMoney(product.product.price)}원
            </td>
          </tr>
          <tr style={{ height: "2vw", borderBottom: "2px solid #d0cfcf" }}>
            <td></td>
            <td style={{ textAlign: "right", paddingRight: "15px" }}>
              x {product.quantity}
            </td>
          </tr>
        </>
      ))}
    </>
  ));

  return (
    <>
      <Title title="주문내역" />
      <div>
        <div className="wrapper">
          <div className="myorder">
            <div className="myorderTitle">주문내역</div>
            <table>
              <colgroup>
                <col style={{ width: "15%" }} />
                <col style={{ width: "70%" }} />
                <col style={{ width: "15%" }} />
              </colgroup>
              <tbody>
                {olist.slice(0, marker)}
                {orderList.length > marker ? (
                  <tr className="loadMore">
                    <td
                      colSpan="4"
                      onClick={() => {
                        console.log("loadMore clicked");

                        loadList();
                      }}
                    >
                      ▼ 더보기
                    </td>
                  </tr>
                ) : (
                  ""
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

                tr:nth-last-child(2) {
                  border: 0;
                }

                .borderRow {
                  height: 2vw;
                  border-bottom: 2px solid #d0cfcf;
                }

                .loadMore {
                  text-align: center;
                  border: 1px solid;
                  cursor: pointer;
                }
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
