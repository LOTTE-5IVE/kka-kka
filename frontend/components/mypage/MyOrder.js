import Title from "../common/Title";
import { useEffect, useState } from "react";
import { GetHApi } from "../../apis/Apis";
import { getToken } from "../../hooks/getToken";
import { commaMoney } from "../../hooks/commaMoney";

export default function MyOrder({ handleDetail, setOrderDetail }) {
  const [token, setToken] = useState("");
  const [orderList, setOrderList] = useState();
  const [moreToggle, setMoreToggle] = useState(true);
  const [lastId, setLastId] = useState();

  const getOrders = async () => {
    GetHApi(`/api/members/me/orders?pageSize=3`, token).then((res) => {
      if (res) {
        setOrderList(res.data);
        setLastId(res.pageInfo.lastId);

        if (res.pageInfo.lastPage) {
          setMoreToggle(false);
        }
      }
    });
  };

  const getMoreOrders = async () => {
    GetHApi(`/api/members/me/orders?pageSize=3&orderId=${lastId}`, token).then(
      (res) => {
        if (res.data.length > 0) {
          setOrderList(orderList.concat(res.data));
          setLastId(res.pageInfo.lastId);
        }

        if (res.pageInfo.lastPage) {
          setMoreToggle(false);
        }
      },
    );
  };

  const totalPriceDiscountApplied = (order) => {
    return order.totalPrice - order.productOrders.reduce((prev, curr) => {
          let discount = (curr.price * (0.01 * curr.discount)) * curr.quantity;
          if (curr.coupon) {
            discount += curr.coupon.discountedPrice;
          }
          return prev + discount
        }, 0
    )
  }

  useEffect(() => {
    setToken(getToken());
    if (token !== "") {
      getOrders();
    }
  }, [token]);

  return (
    <>
      <Title title="주문내역" />
      <div>
        <div className="wrapper">
          <div className="myorder">
            <div className="myorderTitle">주문내역</div>
            {!orderList && (
              <div className="orderEmpty">
                <img
                  className="orderEmptyImg"
                  src="/member/no_item.gif"
                  alt=""
                />
                <span className="orderEmptyComment">주문내역이 없습니다.</span>
              </div>
            )}
            {orderList?.map((order, idx) => {
              return (
                <div key={idx}>
                  <div className="d-flex flex-column">
                    <div className="d-flex align-start flex-column el-container">
                      <div className="d-flex align-start order-title">
                        <span className="title-label mr-2">주문번호</span>
                        <span className="title-id">{order.id}</span>
                      </div>
                      <div>
                        <span className="title-content">
                          총 결제금액: <b>{commaMoney(totalPriceDiscountApplied(order))}</b>원
                        </span>
                        <span className="title-divider">|</span>
                        <span className="title-content">{order.orderedAt}</span>
                      </div>
                    </div>
                    <div
                      className="detail"
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                      }}
                    >
                      <p>
                        {order.productOrders[0].product.name}
                        {order.productOrders.length - 1 > 0 && (
                          <span>외 {order.productOrders.length - 1}건</span>
                        )}
                      </p>
                      <p
                        className="detailBtn"
                        onClick={() => {
                          setOrderDetail(orderList[idx]);
                          handleDetail(true);
                        }}
                      >
                        주문상세 내역보기
                      </p>
                    </div>
                  </div>
                  {orderList.length >= 1 && idx !== orderList.length - 1 && (
                    <p className="order-divider"></p>
                  )}
                </div>
              );
            })}
          </div>
          {orderList && moreToggle && (
            <div
              onClick={getMoreOrders}
              className={"d-flex align-center moreBtn"}
              style={{ cursor: "pointer" }}
            >
              <span>▼ 더보기</span>
            </div>
          )}
        </div>
      </div>
      <style jsx>
        {`
          .orderEmpty {
            display: flex;
            flex-direction: column;

            text-align: center;
            .orderEmptyComment {
              color: #9a9a9a;
            }
          }
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .orderEmpty {
              .orderEmptyImg {
                margin: 70px auto 30px;
                width: 70px;
              }
              .orderEmptyComment {
                margin-bottom: 70px;
                font-size: 18px;
                line-height: 1;
              }
            }

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

            .detail {
              font-size: 16px;
              .detailBtn {
                border: 1px solid #cfcfcf;
                padding: 10px 16px;
                color: #666;
                font-size: 14px;
                cursor: pointer;
              }
            }
            .moreBtn {
              margin-bottom: 2rem;
              padding: 1rem;
              border: 1px solid #c5c5c5;
              color: #525252;
            }

            .myorder {
              .myorderTitle {
                font-size: 24px;
                font-weight: 700;
                color: #3a3a3a;
                border-bottom: 2px solid #3a3a3a;
                line-height: 24px;
                padding-bottom: 15px;
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
            .orderEmpty {
              .orderEmptyImg {
                margin: 70px auto 30px;
                width: 70px;
              }
              .orderEmptyComment {
                margin-bottom: 70px;
                font-size: 18px;
                line-height: 1;
              }
            }

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

            .detail {
              font-size: 12px;
              .detailBtn {
                border: 1px solid #cfcfcf;
                padding: 8px 14px;
                color: #666;
                font-size: 10px;
                cursor: pointer;
              }
            }

            .moreBtn {
              margin-bottom: 2rem;
              padding: 1rem;
              border: 1px solid #c5c5c5;
              color: #525252;
            }

            .myorder {
              .myorderTitle {
                font-size: 24px;
                font-weight: 700;
                color: #3a3a3a;
                border-bottom: 2px solid #3a3a3a;
                line-height: 24px;
                padding-bottom: 15px;
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
            .orderEmpty {
              .orderEmptyImg {
                margin: 70px auto 30px;
                width: 70px;
              }
              .orderEmptyComment {
                margin-bottom: 70px;
                font-size: 18px;
                line-height: 1;
              }
            }

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

            .detail {
              font-size: 12px;
              .detailBtn {
                border: 1px solid #cfcfcf;
                padding: 8px 14px;
                color: #666;
                font-size: 10px;
                cursor: pointer;
              }
            }

            .moreBtn {
              margin-bottom: 1rem;
              padding: 0.5rem;
              border: 1px solid #c5c5c5;
              color: #525252;

              span {
                font-size: 12px;
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
