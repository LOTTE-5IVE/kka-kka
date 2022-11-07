import { commaMoney } from "../../hooks/commaMoney";
import MyProductOrder from "./MyProductOrder";

export default function MyOrderDetail({ orderDetail }) {
  return (
    <>
      <div>
        <div className="wrapper">
          <div className="myorder">
            <div className="myorderTitle">주문내역</div>
            {orderDetail && (
              <div>
                <div className="d-flex flex-column">
                  <div className="d-flex align-start flex-column el-container">
                    <div className="d-flex align-start order-title">
                      <span className="title-label mr-2">주문번호</span>
                      <span className="title-id">{orderDetail.id}</span>
                    </div>
                    <div>
                      <span className="title-content">
                        총 결제금액: <b>{commaMoney(orderDetail.totalPrice)}</b>
                        원
                      </span>
                      <span className="title-divider">|</span>
                      <span className="title-content">
                        {orderDetail.orderedAt}
                      </span>
                    </div>
                    <div className="recipient">
                      <table>
                        <colgroup>
                          <col style={{ width: "25%" }} />
                          <col style={{ width: "75%" }} />
                        </colgroup>
                        <tbody>
                          <tr>
                            <th>받으시는 분</th>
                            <td> {orderDetail.recipient.name}</td>
                          </tr>
                          <tr>
                            <th>전화번호</th>
                            <td> {orderDetail.recipient.phone}</td>
                          </tr>
                          <tr>
                            <th>이메일</th>
                            <td> {orderDetail.recipient.email}</td>
                          </tr>
                          <tr>
                            <th>받으시는 주소</th>
                            <td> {orderDetail.recipient.address}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>

                  {orderDetail.productOrders.map((productOrder, idx) => {
                    return (
                      <div key={idx}>
                        <MyProductOrder productOrder={productOrder} key={idx} />
                        {orderDetail.productOrders.length >= 1 &&
                          idx !== orderDetail.productOrders.length - 1 && (
                            <p className="po-divider"></p>
                          )}
                      </div>
                    );
                  })}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
      <style jsx>
        {`
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .order-divider {
              border-bottom: 1.1px solid #adadad;
              width: 100%;
            }

            .po-divider {
              border-bottom: 1px solid #e0e0e0;
              width: 100%;
            }

            .d-flex {
              display: flex;
              justify-content: center;
            }

            .flex-column {
              flex-direction: column;
            }

            .align-center {
              align-items: center;
            }

            .align-start {
              align-items: start;
            }

            .mr-2 {
              margin-right: 0.2rem;
            }

            .recipient {
              width: 100%;
              margin: 20px 0px;

              table {
                th {
                  text-align: left;
                }
              }
            }

            .myorder {
              .myorderTitle {
                font-size: 24px;
                font-weight: 700;
                color: #3a3a3a;
                border-bottom: 2px solid #3a3a3a;
                line-height: 24px;
                padding-bottom: 15px;
                margin-top: 24px;
              }

              margin-bottom: 3rem;
            }

            .wrapper {
              max-width: 970px;
              width: 100%;
            }

            .el-container {
              width: 100%;
            }

            .order-title {
              margin: 1rem 0 1rem 0;
            }

            .title-label {
              color: #3e3e3e;
            }

            .title-id {
              color: red;
              font-weight: bold;
            }

            .title-content {
              color: #3e3e3e;
              font-weight: normal;
              font-size: 1rem;
            }

            .title-divider {
              color: #3e3e3e;
              margin: 0 1rem 0 1rem;
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .order-divider {
              border-bottom: 1.1px solid #adadad;
              width: 100%;
            }

            .po-divider {
              border-bottom: 1px solid #e0e0e0;
              width: 100%;
            }

            .d-flex {
              display: flex;
              justify-content: center;
            }

            .flex-column {
              flex-direction: column;
            }

            .align-center {
              align-items: center;
            }

            .align-start {
              align-items: start;
            }

            .mr-2 {
              margin-right: 0.2rem;
            }

            .recipient {
              width: 100%;
              margin: 20px 0px;

              table {
                th {
                  text-align: left;
                }
              }
            }

            .myorder {
              .myorderTitle {
                font-size: 20px;
                font-weight: 700;
                color: #3a3a3a;
                border-bottom: 2px solid #3a3a3a;
                line-height: 24px;
                padding-bottom: 15px;
                margin-top: 20px;
              }

              margin-bottom: 3rem;
            }

            .wrapper {
              max-width: 970px;
              width: 100%;
            }

            .el-container {
              width: 100%;
            }

            .order-title {
              margin: 1rem 0 1rem 0;
            }

            .title-label {
              color: #3e3e3e;
            }

            .title-id {
              color: red;
              font-weight: bold;
            }

            .title-content {
              color: #3e3e3e;
              font-weight: normal;
              font-size: 1rem;
            }

            .title-divider {
              color: #3e3e3e;
              margin: 0 1rem 0 1rem;
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .order-divider {
              border-bottom: 1.1px solid #adadad;
              width: 100%;
            }

            .po-divider {
              border-bottom: 1px solid #e0e0e0;
              width: 100%;
            }

            .d-flex {
              display: flex;
              justify-content: center;
            }

            .flex-column {
              flex-direction: column;
            }

            .align-center {
              align-items: center;
            }

            .align-start {
              align-items: start;
            }

            .mr-2 {
              margin-right: 0.2rem;
            }

            .recipient {
              width: 100%;
              margin: 20px 0px;

              table {
                font-size: 10px;
                th {
                  font-size: 12px;
                  text-align: left;
                }
              }
            }

            .myorder {
              .myorderTitle {
                font-size: 16px;
                font-weight: 700;
                color: #3a3a3a;
                border-bottom: 2px solid #3a3a3a;
                line-height: 16px;
                padding-bottom: 10px;
                margin-top: 24px;
              }

              margin-bottom: 3rem;
            }

            .wrapper {
              max-width: 970px;
              width: 100%;
            }

            .el-container {
              width: 100%;
            }

            .order-title {
              margin: 1rem 0 1rem 0;
            }

            .title-label {
              color: #3e3e3e;
            }

            .title-id {
              color: red;
              font-weight: bold;
            }

            .title-content {
              color: #3e3e3e;
              font-weight: normal;
              font-size: 1rem;
            }

            .title-divider {
              color: #3e3e3e;
              margin: 0 1rem 0 1rem;
            }
          }
        `}
      </style>
    </>
  );
}
