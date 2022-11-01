import Link from "next/link";
import { useEffect, useState } from "react";
import { DeleteHApi, GetHApi, PostHApi } from "../../apis/Apis";
import { AdminButton } from "../../components/common/Button/AdminButton";
import ButtonComp from "../../components/common/Button/ButtonComp";
import Title from "../../components/common/Title";
import { CouponDown } from "../../components/coupon/CouponDown";
import { CouponModal } from "../../components/coupon/CouponModal";
import { useGetToken } from "../../hooks/useGetToken";
import { useMoney } from "../../hooks/useMoney";
import { NGray } from "../../typings/NormalColor";

export default function cart() {
  const [token, setToken] = useState("");
  const [modal, setModal] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const [checkItemsIdx, setCheckItemsIdx] = useState([]);
  const [checkItems, setCheckItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [discountPrice, setDiscountPrice] = useState(0);

  const handleSingleCheck = (checked, product) => {
    if (checked) {
      setCheckItems((prev) => [...prev, product]);
      setCheckItemsIdx((prev) => [...prev, product.id]);
    } else {
      setCheckItems(checkItems.filter((el) => el !== product));
      setCheckItemsIdx(checkItemsIdx.filter((el) => el !== product.id));
    }
  };

  const handleAllCheck = (checked) => {
    if (checked) {
      const idArray = [];
      const itemArry = [];
      cartItems.forEach((el) => {
        idArray.push(el.id);
        itemArry.push(el);
      });

      setCheckItems(itemArry);
      setCheckItemsIdx(idArray);
    } else {
      setCheckItems([]);
      setCheckItemsIdx([]);
    }
  };

  function handleModal() {
    setModal(false);
  }

  const handlePlus = (id) => {
    setCartItems(
      cartItems.map((product) =>
        product.productId === id
          ? { ...product, quantity: product.quantity + 1 }
          : product,
      ),
    );
  };

  const handleMinus = (id) => {
    setCartItems(
      cartItems.map((product) =>
        product.productId === id && product.quantity > 1
          ? { ...product, quantity: product.quantity - 1 }
          : product,
      ),
    );
  };

  const getCartItem = async () => {
    GetHApi("/api/carts", token).then((res) => {
      if (res) {
        console.log(res.cartItems);
        setCartItems(res.cartItems);
        setTotalPrice(
          res.cartItems.reduce(
            (prev, cur) => prev + cur.price * cur.quantity,
            0,
          ),
        );
        setDiscountPrice(
          res.cartItems.reduce(
            (prev, cur) =>
              prev +
              Math.ceil(cur.price * 0.01 * cur.productDiscount * cur.quantity),
            0,
          ),
        );
      }
    });
  };

  const editCartItem = async (id, quantity) => {
    if (quantity > 1) {
      PostHApi("/api/carts", { productId: id, quantity: quantity }, token).then(
        (res) => {},
      );
    }
  };
  const removeCartItem = async (id) => {
    DeleteHApi(`/api/carts/${id}`, token).then((res) => {
      getCartItem();
    });
  };

  useEffect(() => {
    setToken(useGetToken());

    if (token !== "") {
      getCartItem();
    }
  }, [token]);

  return (
    <>
      <Title title="장바구니" />
      <div className="CartTitle">
        <h2>장바구니</h2>
      </div>
      <div className="CartContents">
        <div className="orderTables">
          <div className="orderList">
            <table>
              <colgroup>
                <col style={{ width: "5%" }} />
                <col style={{ width: "10%" }} />
                <col style={{ width: "40%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "10%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "5%" }} />
              </colgroup>
              <thead>
                <tr style={{ height: "57px" }}>
                  <th scope="col">
                    {cartItems ? (
                      <input
                        type="checkbox"
                        name="select-all"
                        onChange={(e) => handleAllCheck(e.target.checked)}
                        checked={
                          checkItemsIdx.length === cartItems.length
                            ? true
                            : false
                        }
                      />
                    ) : (
                      <input type="checkbox" checked={false} />
                    )}
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
                {cartItems?.map((product, index) => {
                  return (
                    <tr key={product.productId}>
                      <td>
                        <input
                          type="checkbox"
                          name={`select-${product.id}`}
                          onChange={(e) =>
                            handleSingleCheck(e.target.checked, product)
                          }
                          checked={
                            checkItemsIdx.includes(product.id) ? true : false
                          }
                        />
                      </td>
                      <td>
                        <img className="thumnail" src={product.imageUrl} />
                      </td>
                      <td>
                        <div>{product.productName}</div>
                        <div className="couponWrapper">
                          <div className="couponContents">
                            <span>coupon</span>{" "}
                            <input
                              type="text"
                              size="15"
                              defaultValue=""
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
                                  <CouponDown handleModal={handleModal} />
                                </div>
                              </CouponModal>
                            )}
                          </div>
                        </div>
                      </td>
                      <td>
                        <span>
                          <input
                            className="minusBtn"
                            type="button"
                            onClick={() => {
                              editCartItem(
                                product.productId,
                                product.quantity - 1,
                              );
                              handleMinus(product.productId);
                            }}
                            value="-"
                          />
                          <span>{product.quantity}</span>
                          <input
                            className="plusBtn"
                            type="button"
                            onClick={() => {
                              editCartItem(
                                product.productId,
                                product.quantity + 1,
                              );
                              handlePlus(product.productId);
                            }}
                            value="+"
                          />
                        </span>
                      </td>
                      <td>무료</td>
                      <td>
                        {product.productDiscount ? (
                          <>
                            <p>
                              {useMoney(
                                Math.ceil(
                                  product.price *
                                    (1 - 0.01 * product.productDiscount),
                                ) * product.quantity,
                              )}
                              원
                            </p>
                            <p>
                              <span>
                                {useMoney(product.price * product.quantity)}원
                              </span>
                            </p>
                          </>
                        ) : (
                          <>
                            <p>
                              {useMoney(product.price * product.quantity)}원
                            </p>
                          </>
                        )}
                      </td>
                      <td
                        onClick={() => {
                          removeCartItem(product.id);
                        }}
                      >
                        <img width="20px" src="/common/cancelred.png" />
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>

          <div className="orderSummary">
            <table>
              <colgroup>
                <col style={{ width: "269px" }} />
                <col style={{ width: "211px" }} />
                <col style={{ width: "269px" }} />
                <col style={{ width: "211px" }} />
                <col style={{ width: "269px" }} />
              </colgroup>
              <thead>
                <tr style={{ height: "58px" }}>
                  <th scope="col">총 상품금액</th>
                  <th scope="col"></th>
                  <th scope="col">총 할인금액</th>
                  <th scope="col"></th>
                  <th scope="col">결제예정금액</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <span>{useMoney(totalPrice)}</span>
                  </td>
                  <td>-</td>
                  <td>
                    <span>{useMoney(discountPrice) || 0}</span>
                  </td>
                  <td>=</td>
                  <td>{useMoney(totalPrice - discountPrice)}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div className="orderBtn">
          <Link
            href={{
              pathname: `/payment`,
              query: { orderItems: JSON.stringify(cartItems) },
            }}
            as={`/payment`}
          >
            <a>
              <ButtonComp context="전체상품 주문" />
            </a>
          </Link>
          <Link
            href={{
              pathname: `/payment`,
              query: { orderItems: JSON.stringify(checkItems) },
            }}
            as={`/payment`}
          >
            <a>
              <ButtonComp context="선택상품 주문" />
            </a>
          </Link>
        </div>
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .CartTitle {
            margin-top: 96px;
            text-align: center;
            padding: 0;
            color: #3a3a3a;
            font-size: 30px;
            font-weight: 700;
            line-height: 1;
          }

          .CartContents {
            text-align: center;

            .orderTables {
              display: flex;
              flex-direction: column;
              align-items: center;

              .orderList {
                table {
                  width: 1235px;
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

                tr {
                  height: 115px;
                }

                td {
                  .thumnail {
                    width: 50px;
                  }
                  img {
                    width: 20px;
                  }

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

                  .minusBtn {
                    margin-right: 15px;
                  }
                  .plusBtn {
                    margin-left: 15px;
                  }

                  p {
                    margin: 0;

                    span {
                      font-size: 14px;
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }
                }
              }
            }

            .orderSummary {
              margin-top: 96px;

              table {
                width: 1235px;
                border-collapse: collapse;
                border-bottom: 1px solid #dfdfdf;

                tbody {
                  tr {
                    height: 134px;
                  }
                }
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

            .orderBtn {
              margin: 38px auto 58px;
              width: 571px;
              display: flex;
              justify-content: space-around;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .CartTitle {
            text-align: center;
            padding: 0;
            color: #3a3a3a;
            font-size: 30px;
            font-weight: 700;
            line-height: 1;
          }

          .CartContents {
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
                  .thumnail {
                    width: 50px;
                  }
                  img {
                    width: 20px;
                  }

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

                  .minusBtn {
                    margin: 0;
                    position: relative;
                    width: 15px;
                    height: 15px;
                    left: 7px;
                    top: 25px;
                  }
                  .plusBtn {
                    margin: 0;
                    position: relative;
                    width: 15px;
                    height: 15px;
                    left: 18px;
                    top: 6px;
                  }
                  p {
                    margin: 0;

                    span {
                      font-size: 14px;
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }
                }
              }
            }

            .orderSummary {
              margin-top: 96px;

              table {
                border-collapse: collapse;
                border-bottom: 1px solid #dfdfdf;

                tbody {
                  tr {
                    height: 80px;
                  }
                }
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

            .orderBtn {
              margin: 38px auto 58px;
              width: 571px;
              display: flex;
              justify-content: space-around;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .CartTitle {
            text-align: center;
            padding: 0;
            color: #3a3a3a;
            font-size: 20px;
            font-weight: 700;
            line-height: 1;
          }

          .CartContents {
            text-align: center;

            .orderTables {
              display: flex;
              flex-direction: column;
              align-items: center;

              .orderList {
                table {
                  width: 450px;
                  font-size: 12px;
                  border-collapse: collapse;
                  border-bottom: 1px solid #dfdfdf;

                  th {
                    border: 0;
                    margin: 0;
                    padding: 0;

                    border-spacing: 0;
                    border-top: 2px solid #1a1a1a;
                    border-bottom: 1px solid #dfdfdf;
                  }

                  tr {
                    height: 75px;
                  }

                  td {
                    font-size: 10px;

                    .thumnail {
                      width: 30px;
                    }
                    img {
                      width: 15px;
                    }

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
                          font-size: 10px;
                          background-color: #05c7f2;
                          color: #fff;
                        }
                      }
                    }

                    input[type="text"] {
                      width: 100px;
                    }

                    input[type="button"] {
                      background-color: #fff;
                      border: 1px solid #c8c8c8;
                      border-radius: 50%;
                    }

                    .minusBtn {
                      padding: 0;
                      position: relative;
                      width: 15px;
                      height: 15px;
                      left: 0px;
                      top: 20px;
                    }
                    .plusBtn {
                      padding: 0;
                      position: relative;
                      width: 15px;
                      height: 15px;
                      left: 3px;
                      top: 20px;
                    }

                    p {
                      margin: 0;

                      span {
                        font-size: 14px;
                        color: ${NGray};
                        text-decoration: line-through;
                      }
                    }
                  }
                }
              }
            }

            .orderSummary {
              margin-top: 30px;

              table {
                width: 450px;
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

            .orderBtn {
              margin: 38px auto 58px;
              width: 480px;
              display: flex;
              justify-content: space-around;
            }
          }
        }
      `}</style>
    </>
  );
}
