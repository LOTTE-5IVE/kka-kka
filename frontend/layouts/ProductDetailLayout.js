import Link from "next/link";
import { AdminButton } from "../components/common/Button/AdminButton";
import { CouponDown } from "../components/coupon/CouponDown";
import { CouponModal } from "../components/coupon/CouponModal";
import Info from "../components/product/productDetail/Info";
import Nutri from "../components/product/productDetail/Nutri";
import Review from "../components/product/productDetail/Review";
import { useMoney } from "../hooks/useMoney";
import { NBlack, NGray, NLightGray } from "../typings/NormalColor";
import {
  ThemeBlue,
  ThemeGray,
  ThemePink,
  ThemeRed,
} from "../typings/ThemeColor";

export default function ProductDetailLayout({
  tab,
  modal,
  product,
  reviews,
  quantity,
  handleModal,
  handleTab,
  handleQuantity,
  addCartItem,
}) {
  return (
    <>
      {product && (
        <div className="wrapper" key={product.id}>
          <div className="detailTop">
            <div className="detailImg">
              {/* <ProductRec id={product.id} imgsrc={product.image_url} /> */}
              <img src={`${product.image_url}`} />
            </div>

            <div className="detailEtc" key={product.id}>
              <div className="headingArea">
                <div className="headingAreaName">{product.name}</div>

                <div className="headingDescription">
                  {product.discount !== 0 ? (
                    <>
                      <p style={{ color: `${ThemeRed}` }}>
                        {product.discount}%
                      </p>
                      <p>
                        {useMoney(
                          product.price * (1 - 0.01 * product.discount),
                        )}
                        원 <span>{useMoney(product.price)}원</span>
                      </p>
                    </>
                  ) : (
                    <>
                      <p>{useMoney(product.price)}원</p>
                    </>
                  )}
                </div>
              </div>
              <div className="delivery">
                <p>배송비</p> <p style={{ color: `${NGray}` }}>무료</p>
              </div>
              <div className="coupon">
                <p>고객님께만 드리는 쿠폰이 있어요</p>{" "}
                <div
                  onClick={() => {
                    handleModal(true);
                  }}
                >
                  <AdminButton context="쿠폰받기" color="red" width="70px" />
                </div>
                {modal && (
                  <CouponModal>
                    <div>
                      <CouponDown handleModal={handleModal} />
                    </div>
                  </CouponModal>
                )}
              </div>
              <div className="totalProducts">
                <table>
                  <colgroup>
                    <col style={{ width: "20vw" }} />
                    <col style={{ width: "7vw" }} />
                  </colgroup>

                  <tbody>
                    <tr>
                      <td colSpan="2">{product.name}</td>
                    </tr>
                    <tr style={{ height: "3vw" }}>
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
                      <td style={{ textAlign: "right" }}>
                        {useMoney(product.price * quantity)}원
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div className="totalPrice">
                <p>
                  <strong>TOTAL</strong>
                </p>
                <p>
                  <span>{useMoney(product.price * quantity)}원</span>({quantity}
                  개)
                </p>
              </div>
              <div className="btn">
                <div
                  className="cartBtn"
                  onClick={() => {
                    addCartItem(product, quantity);
                  }}
                >
                  장바구니
                </div>

                <Link
                  href={{
                    pathname: `/payment`,
                    query: {
                      buyItem: JSON.stringify(product),
                      quantity: quantity,
                    },
                  }}
                  as={`/payment`}
                >
                  <div className="buyBtn">바로 구매</div>
                </Link>
              </div>
            </div>
          </div>
          <div className="detailBottom">
            <div className="tabMenu">
              <ul>
                <li onClick={() => handleTab("info")}>
                  <a className={`${tab === "info" ? "active" : ""}`}>
                    상품정보
                  </a>
                </li>
                <li onClick={() => handleTab("nutri")}>
                  <a className={`${tab === "nutri" ? "active" : ""}`}>
                    영양정보
                  </a>
                </li>
                <li onClick={() => handleTab("review")}>
                  <a className={`${tab === "review" ? "active" : ""}`}>
                    상품후기
                  </a>
                </li>
              </ul>
            </div>
            <div className="descriptions">
              {tab == "info" ? (
                <Info detailImage_url={product.detailImage_url} />
              ) : tab == "nutri" ? (
                <Nutri nutritionInfo_url={product.nutritionInfo_url} />
              ) : (
                <Review reviews={reviews} />
              )}
            </div>
          </div>
        </div>
      )}
      <style jsx>{`
        .wrapper {
          margin: 3% auto 0;
          width: 65%;

          .detailTop {
            display: flex;
            justify-content: space-between;

            .detailImg {
              text-align: center;
              border-radius: 30px;
              background-color: ${ThemeGray};
              height: 32vw;

              img {
                max-height: 600px;
                height: 32vw;
              }
            }

            .detailEtc {
              width: 45%;

              .headingArea {
                font-size: 34px;
                line-height: 40px;
                color: ${NBlack};
                font-weight: 700;
                .headingAreaName {
                  margin-top: 23px;
                }

                .headingDescription {
                  font-size: 32px;
                  margin: 28px 0 0;
                  height: 140px;
                  padding-bottom: 30px;
                  border-bottom: 1px solid ${NLightGray};

                  p {
                    margin: 0;

                    span {
                      font-size: 24px;
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }
                }
              }
              .delivery {
                margin: 20px 0 10px;
                font-size: 16px;
                display: flex;

                p {
                  width: 120px;
                }
              }

              .coupon {
                margin: 20px 0 10px;
                font-size: 16px;
                display: flex;
                align-items: center;
                p {
                  width: 100%;
                  font-size: 12px;
                  color: red;
                  font-weight: 700;
                }
              }
              .totalProducts {
                /* background-color: #f8f8f8; */
                background-color: ${ThemeGray};
                margin-bottom: 5px;
                padding: 20px 15px 15px;
                border-radius: 8px;

                input[type="button"] {
                  background-color: #fff;
                  /* border: 1px solid #c8c8c8; */
                  border: 1px solid ${NGray};
                  border-radius: 50%;
                }
              }

              .totalPrice {
                padding: 30px 0 10px;
                display: flex;
                justify-content: space-between;
                align-items: center;

                strong {
                  font-size: 18px;
                  color: ${NBlack};
                  font-weight: 500;
                }

                p {
                  float: right;
                  line-height: 33px;
                  font-size: 14px;
                  color: ${NBlack};
                  font-weight: 500;
                  span {
                    font-size: 32px;
                    color: ${ThemeRed};
                    font-weight: 700;
                  }
                }
              }

              .btn {
                display: flex;
                padding: 30px 0 10x;
                justify-content: space-around;

                .cartBtn {
                  width: 45%;
                  margin-top: 5px;
                  background: ${ThemePink};
                  color: #fff;
                  border: 1px solid ${ThemePink};
                  text-align: center;
                  height: 60px;
                  line-height: 60px;
                  font-size: 18px;
                  vertical-align: middle;
                  padding: 0;
                  cursor: pointer;
                }

                .buyBtn {
                  width: 45%;
                  margin-top: 5px;
                  background: ${ThemeBlue};
                  color: #fff;
                  border: 1px solid ${ThemeBlue};
                  text-align: center;
                  height: 60px;
                  line-height: 60px;
                  font-size: 18px;
                  padding: 0;
                  cursor: pointer;
                }

                .cartBtn:hover {
                  background-color: #fff;
                  color: ${ThemePink};
                  transition: 0.7s;
                }

                .buyBtn:hover {
                  background-color: #fff;
                  color: ${ThemeBlue};
                  transition: 0.7s;
                }
              }
            }
          }

          .detailBottom {
            padding-top: 40px;
            .tabMenu {
              margin: 0 auto 60px;
              border-bottom: 2px solid ${NLightGray};

              ul {
                margin: 0 auto;
                display: flex;
                justify-content: center;
                text-align: center;
                list-style: none;

                li {
                  position: relative;
                  display: inline-block;
                  width: 280px;
                  height: 60px;
                  line-height: 60px;

                  a {
                    display: block;
                    font-size: 18px;
                    font-weight: 700;

                    color: ${NGray};
                    text-align: center;
                    cursor: pointer;
                  }

                  .active {
                    color: #000;
                    border-bottom: 2px solid ${ThemeRed};
                  }

                  a:hover {
                    color: ${NBlack};
                  }
                }
              }
            }
          }
        }
      `}</style>
    </>
  );
}
