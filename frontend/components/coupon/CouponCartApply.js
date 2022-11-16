import DownloadIcon from "@mui/icons-material/Download";
import { useContext, useState } from "react";
import { useEffect } from "react";
import { GetHApi, PostHApi } from "../../apis/Apis";
import { getToken } from "../../hooks/getToken";
import { commaMoney } from "../../hooks/commaMoney";
import { NGray } from "../../typings/NormalColor";
import { PaymentContext } from "../../context/PaymentContext";
export function CouponCartApply({
  id,
  modalVisibleId,
  setModalVisibleId,
  cartItemId,
  product,
}) {
  const [token, setToken] = useState("");
  const [coupons, setCoupons] = useState();
  const { payment, setPayment } = useContext(PaymentContext);

  const getProductMemberCoupon = async () => {
    await GetHApi(`/api/coupons/me/products/${product.id}`, token).then(
      (res) => {
        setCoupons(res);
      },
    );
  };

  const adaptCoupon = async (couponId) => {
    if (cartItemId) {
      await PostHApi(`/api/carts/${cartItemId}/${couponId}`, null, token).then(
        (res) => {
          setPayment(
            payment.map((product) =>
              product.cartItemId === res.cartItemId ? res : product,
            ),
          );

          onCloseHandler();
        },
      );
    } else {
      await PostHApi(
        `/api/orders/${couponId}`,
        { productId: product.id, quantity: product.quantity },
        token,
      ).then((res) => {
        // product = res;
        setPayment([res]);
        onCloseHandler();
      });
    }
  };

  const downloadCoupon = async (id) => {
    if (isLogin()) {
      await PostHApi(`/api/coupons/${product.id}/${id}`, null, token).then(
        (res) => {
          getProductMemberCoupon();
          alert("다운 완료");
        },
      );
    } else {
      alert("로그인이 필요한 서비스입니다.");
    }
  };

  const onCloseHandler = () => {
    setModalVisibleId("");
  };

  useEffect(() => {
    setToken(getToken());
    if (token !== "") {
      getProductMemberCoupon();
    }
  }, [token]);

  return (
    <>
      <div className={modalVisibleId === id ? "wrapper" : "none"}>
        <div
          style={{
            display: "flex",
            justifyContent: "end",
            width: "98%",
            margin: "10px 0",
            cursor: "pointer",
          }}
          onClick={() => {
            onCloseHandler();
          }}
        >
          <img width="24px" src="/common/cancel.png" alt="" />
        </div>
        <div className="container" style={{ textAlign: "left" }}>
          <p>상품 구매 시 사용 가능한 할인쿠폰입니다.</p>
          <table>
            <colgroup>
              <col style={{ width: "20%" }} />
              <col style={{ width: "50%" }} />
              <col style={{ width: "30%" }} />
            </colgroup>
            <thead style={{ height: "48px" }}>
              <tr>
                <th colSpan="2">상품정보</th>
                <th>상품가격</th>
              </tr>
            </thead>
            <tbody>
              <tr style={{ height: "71px", borderBottom: "1px solid #dedede" }}>
                <td>
                  <img width="64px" src={product.imageUrl} alt="" />
                </td>
                <td style={{ textAlign: "left" }}>{product.name}</td>
                <td>
                  {product.discount ? (
                    <>
                      <p style={{ marginBottom: "0" }}>
                        {commaMoney(
                          Math.ceil(
                            product.price * (1 - 0.01 * product.discount),
                          ) * product.quantity,
                        )}
                        원
                      </p>
                      <p style={{ marginBottom: "0" }}>
                        <span>
                          {commaMoney(product.price * product.quantity)}원
                        </span>
                      </p>
                    </>
                  ) : (
                    <>
                      <p style={{ marginBottom: "0" }}>
                        {commaMoney(product.price * product.quantity)}원
                      </p>
                    </>
                  )}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="ownContainer" style={{ textAlign: "left" }}>
          <p>적용 가능한 쿠폰</p>
          <div className="tableWrapper">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "10%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "5%" }} />
              </colgroup>
              <thead style={{ height: "59px" }}>
                <tr>
                  <th>쿠폰명</th>
                  <th>할인</th>
                  <th>최소 주문금액</th>
                  <th>최대 할인금액</th>
                  <th>사용기한</th>
                  <th>적용가</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {coupons
                  ?.filter(
                    (coupon) =>
                      !coupon.isDownloadable &&
                      product.price *
                        product.quantity *
                        (1 - 0.01 * product.discount) >
                        coupon.minOrderPrice,
                  )
                  .map((coupon, idx) => {
                    return (
                      <tr
                        key={idx}
                        style={{
                          height: "59px",
                          borderBottom: "1px solid #dedede",
                        }}
                      >
                        <td>{coupon.name}</td>
                        <td>
                          {coupon.percentage ? `${coupon.percentage}%` : "X"}
                        </td>
                        <td>{commaMoney(coupon.minOrderPrice)}원</td>
                        <td>{commaMoney(coupon.maxDiscount)}원</td>
                        <td>{coupon.expiredAt.slice(0, 10)}</td>
                        <td>
                          {commaMoney(
                            coupon.discountedPrice * product.quantity,
                          )}
                          원
                        </td>
                        <td
                          style={{
                            height: "59px",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                          }}
                        >
                          <div
                            style={{ cursor: "pointer" }}
                            onClick={() => {
                              adaptCoupon(coupon.id);
                            }}
                          >
                            적용
                          </div>
                        </td>
                      </tr>
                    );
                  })}
              </tbody>
            </table>
          </div>
        </div>
        <div className="ownContainer" style={{ textAlign: "left" }}>
          <p>보유한 쿠폰</p>
          <div className="tableWrapper">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "10%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "5%" }} />
              </colgroup>
              <thead style={{ height: "59px" }}>
                <tr>
                  <th>쿠폰명</th>
                  <th>할인</th>
                  <th>최소 주문금액</th>
                  <th>최대 할인금액</th>
                  <th>사용기한</th>
                  <th>적용가</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {coupons
                  ?.filter((coupon) => !coupon.isDownloadable)
                  .map((coupon, idx) => {
                    return (
                      <tr
                        key={idx}
                        style={{
                          height: "59px",
                          borderBottom: "1px solid #dedede",
                        }}
                      >
                        <td>{coupon.name}</td>
                        <td>
                          {coupon.percentage ? `${coupon.percentage}%` : "X"}
                        </td>
                        <td>{commaMoney(coupon.minOrderPrice)}원</td>
                        <td>{commaMoney(coupon.maxDiscount)}원</td>
                        <td>{coupon.expiredAt.slice(0, 10)}</td>
                        <td>
                          {commaMoney(
                            coupon.discountedPrice * product.quantity,
                          )}
                          원
                        </td>
                        <td
                          style={{
                            height: "59px",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                          }}
                        ></td>
                      </tr>
                    );
                  })}
              </tbody>
            </table>
          </div>
        </div>
        <div className="totalContainer" style={{ textAlign: "left" }}>
          <p>다운로드 가능한 쿠폰</p>
          <div className="tableWrapper">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "10%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "5%" }} />
              </colgroup>
              <thead style={{ height: "59px" }}>
                <tr>
                  <th>쿠폰명</th>
                  <th>할인</th>
                  <th>최소 주문금액</th>
                  <th>최대 할인금액</th>
                  <th>사용기한</th>
                  <th>적용가</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {coupons
                  ?.filter((coupon) => coupon.isDownloadable)
                  .map((coupon, idx) => {
                    return (
                      <tr
                        key={idx}
                        style={{
                          height: "59px",
                          borderBottom: "1px solid #dedede",
                        }}
                      >
                        <td>{coupon.name}</td>
                        <td>
                          {coupon.percentage ? `${coupon.percentage}%` : "X"}
                        </td>
                        <td>{commaMoney(coupon.minOrderPrice)}원</td>
                        <td>{commaMoney(coupon.maxDiscount)}원</td>
                        <td>{coupon.expiredAt.slice(0, 10)}</td>
                        <td>{commaMoney(coupon.discountedPrice)}원</td>
                        <td
                          style={{
                            height: "59px",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                          }}
                        >
                          {coupon.isDownloadable ? (
                            <DownloadIcon
                              style={{ cursor: "pointer" }}
                              onClick={() => {
                                downloadCoupon(coupon.id);
                              }}
                            />
                          ) : (
                            "✔"
                          )}
                        </td>
                      </tr>
                    );
                  })}
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <style jsx>{`
        .none {
          display: none;
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            width: 800px;
            min-height: 900px;
            margin-top: 5px;
            .container {
              p {
                width: 90%;
                margin: 0 auto 10px;
                font-weight: bold;

                span {
                  font-size: 14px;
                  color: ${NGray};
                  text-decoration: line-through;
                }
              }

              table {
                width: 90%;
                border-collapse: collapse;
                margin: 0 auto;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px solid;
                  border-bottom: 1px solid;
                }
              }
            }

            .tableWrapper {
              max-height: 200px;
              overflow: auto;
              margin: auto;
              width: 90%;

              table {
                width: 100%;
              }
            }

            .totalContainer {
              padding-bottom: 100px;
            }

            .ownContainer,
            .totalContainer {
              margin-top: 30px;
              max-height: 400px;
              overflow: auto;

              p {
                font-size: 18px;
                width: 90%;
                margin: 0 auto 15px;
                font-weight: bold;
              }

              table {
                border-collapse: collapse;
                margin: 0 auto;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px double;
                  border-bottom: 1px double;
                }
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            width: 80vw;
            min-height: 90vw;
            margin-top: 1vw;
            .container {
              p {
                width: 90%;
                margin: 0 auto 3vw;
                font-weight: bold;

                span {
                  font-size: 1.5vw;
                  color: ${NGray};
                  text-decoration: line-through;
                }
              }

              table {
                width: 90%;
                border-collapse: collapse;
                margin: 0 auto;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px solid;
                  border-bottom: 1px solid;
                }
              }
            }

            .tableWrapper {
              max-height: 50vw;
              overflow: auto;
              margin: auto;
              width: 90%;

              table {
                width: 100%;
              }
            }

            .totalContainer {
              padding-bottom: 5vw;
            }

            .ownContainer,
            .totalContainer {
              margin-top: 3vw;
              p {
                font-size: 2.5vw;
                width: 90%;
                margin: 0 auto 2vw;
                font-weight: bold;
              }

              table {
                border-collapse: collapse;
                margin: 0 auto;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px double;
                  border-bottom: 1px double;
                }
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            width: 380px;
            min-height: 500px;
            margin-top: 5px;
            .container {
              p {
                width: 90%;
                margin: 0 auto 10px;
                font-weight: bold;
                font-size: 12px;

                span {
                  font-size: 10px;
                  color: ${NGray};
                  text-decoration: line-through;
                }
              }

              table {
                width: 90%;
                border-collapse: collapse;
                margin: 0 auto;
                font-size: 12px;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px solid;
                  border-bottom: 1px solid;
                }
              }
            }

            .tableWrapper {
              max-height: 250px;
              overflow: auto;
              margin: auto;
              width: 90%;

              table {
                width: 100%;
              }
            }

            .totalContainer {
              padding-bottom: 30px;
            }

            .ownContainer,
            .totalContainer {
              margin-top: 20px;
              p {
                font-size: 12px;
                width: 90%;
                margin: 0 auto 15px;
                font-weight: bold;
              }

              table {
                border-collapse: collapse;
                margin: 0 auto;
                font-size: 12px;
                text-align: center;

                thead {
                  height: 4vw;
                  border-top: 1px double;
                  border-bottom: 1px double;
                }
              }
            }
          }
        }
      `}</style>
    </>
  );
}
