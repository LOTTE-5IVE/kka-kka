import Link from "next/link";
import { useState } from "react";
import { AdminButton } from "../../components/admin/AdminButton";
import ButtonComp from "../../components/common/buttonComp";
import Title from "../../components/common/Title";
import { CouponDown } from "../../components/coupon/CouponDown";
import { CouponModal } from "../../components/coupon/CouponModal";
import SNSButton from "../../components/member/SNSButton";

export default function cart() {
  const [quantity, setQuantity] = useState(1);
  const [modal, setModal] = useState(false);
  const data = [
    { id: 0, title: "선택 1" },
    { id: 1, title: "선택 2" },
    { id: 2, title: "선택 3" },
    { id: 3, title: "선택 4" },
  ];

  const [checkItems, setCheckItems] = useState([]);

  const handleSingleCheck = (checked, id) => {
    if (checked) {
      setCheckItems((prev) => [...prev, id]);
    } else {
      setCheckItems(checkItems.filter((el) => el !== id));
    }
  };

  const handleAllCheck = (checked) => {
    if (checked) {
      const idArray = [];
      data.forEach((el) => idArray.push(el.id));
      setCheckItems(idArray);
    } else {
      setCheckItems([]);
    }
  };

  function modalHandler() {
    setModal(false);
  }
  const handleQuantity = (type) => {
    if (type !== "minus" || quantity > 1) {
      type === "plus" ? setQuantity(quantity + 1) : setQuantity(quantity - 1);
    }
  };

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
                    <input
                      type="checkbox"
                      name="select-all"
                      onChange={(e) => handleAllCheck(e.target.checked)}
                      checked={checkItems.length === data.length ? true : false}
                    />
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
                    <input
                      type="checkbox"
                      name={`select-${data.id}`}
                      onChange={(e) =>
                        handleSingleCheck(e.target.checked, data.id)
                      }
                      checked={checkItems.includes(data.id) ? true : false}
                    />
                  </td>
                  <td>
                    <img width="50px" src="/sample.png" />
                  </td>
                  <td>
                    <div>몽쉘 생크림 오리지널 192g</div>
                    <div className="couponWrapper">
                      <div className="couponContents">
                        <span>coupon</span>{" "}
                        <input
                          type="text"
                          size="15"
                          defaultValue="뭐시기 쿠폰"
                          readOnly
                        />
                      </div>
                      <div>
                        <div
                          onClick={() => {
                            setModal(true);
                          }}
                        >
                          <AdminButton
                            context="쿠폰적용"
                            color="#05c7f2"
                            width="60px"
                          />
                        </div>
                        {modal && (
                          <CouponModal>
                            <div>
                              <CouponDown modalHandler={modalHandler} />
                            </div>
                          </CouponModal>
                        )}
                      </div>
                    </div>
                  </td>
                  <td>
                    <span>
                      <input
                        type="button"
                        onClick={() => handleQuantity("minus")}
                        value="-"
                        style={{ marginRight: "15px" }}
                      />
                      <span>{quantity}</span>
                      <input
                        type="button"
                        onClick={() => handleQuantity("plus")}
                        value="+"
                        style={{ marginLeft: "15px" }}
                      />
                    </span>
                  </td>
                  <td>무료</td>
                  <td>4,500원</td>
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

              td {
                .couponWrapper {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  margin-top: 10px;

                  .couponContents {
                    display: flex;
                    line-height: 15px;
                    margin-right: 10px;

                    span {
                      background-color: #05c7f2;
                      color: #fff;
                    }
                  }
                }

                input[type="button"] {
                  background-color: #fff;
                  border: 1px solid #c8c8c8;
                  border-radius: 50%;
                }
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
