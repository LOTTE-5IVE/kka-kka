import Link from "next/link";
import { useEffect, useState } from "react";
import { DeleteHApi, GetHApi, PostHApi } from "../../apis/Apis";
import { AdminButton } from "../../components/common/Button/AdminButton";
import ButtonComp from "../../components/common/Button/buttonComp";
import Title from "../../components/common/Title";
import { CouponDown } from "../../components/coupon/CouponDown";
import { CouponModal } from "../../components/coupon/CouponModal";
import { useGetToken } from "../../hooks/useGetToken";
import { useMoney } from "../../hooks/useMoney";

export default function cart() {
  const [token, setToken] = useState("");
  const [modal, setModal] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const [checkItemsIdx, setCheckItemsIdx] = useState([]);
  const [checkItems, setCheckItems] = useState([]);

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
        setCartItems(res.cartItems);
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
                    <tr key={product.productId} style={{ height: "6vw" }}>
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
                        <img width="50px" src={product.imageUrl} />
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
                            type="button"
                            onClick={() => {
                              editCartItem(
                                product.productId,
                                product.quantity - 1,
                              );
                              handleMinus(product.productId);
                            }}
                            value="-"
                            style={{ marginRight: "15px" }}
                          />
                          <span>{product.quantity}</span>
                          <input
                            type="button"
                            onClick={() => {
                              editCartItem(
                                product.productId,
                                product.quantity + 1,
                              );
                              handlePlus(product.productId);
                            }}
                            value="+"
                            style={{ marginLeft: "15px" }}
                          />
                        </span>
                      </td>
                      <td>무료</td>
                      <td>{useMoney(product.price * product.quantity)}원</td>
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
                  <td>
                    {/* {checkItems.map((product, index) => {
                      return <span key={index}> {product.id}</span>;
                    })} */}
                    상품금액
                  </td>
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
