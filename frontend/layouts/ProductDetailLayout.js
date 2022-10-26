import Link from "next/link";
import { AdminButton } from "../components/common/Button/AdminButton";
import { CouponDown } from "../components/coupon/CouponDown";
import { CouponModal } from "../components/coupon/CouponModal";
import Info from "../components/product/productDetail/Info";
import Nutri from "../components/product/productDetail/Nutri";
import Review from "../components/product/productDetail/Review";
import { isLogin } from "../hooks/isLogin";
import { useMoney } from "../hooks/useMoney";
import { NBlack, NGray, NLightGray } from "../typings/NormalColor";
import {
  ThemeBlue,
  ThemeGray,
  ThemePink,
  ThemeRed,
} from "../typings/ThemeColor";
import RangeWithIcons from "../components/mypage/review/RangeWithIcons";

export default function ProductDetailLayout({
  tab,
  modal,
  product,
  page,
  setPage,
  lastPage,
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
        <div className="ProductDetailLWrapper" key={product.id}>
          <div className="detailTop">
            <div className="detailImg">
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
                          Math.ceil(
                            product.price * (1 - 0.01 * product.discount),
                          ),
                        )}
                        원 <span>{useMoney(product.price)}원</span>
                      </p>
                    </>
                  ) : (
                    <>
                      <p>{useMoney(product.price)}원</p>
                    </>
                  )}
                  <div className="mt-3">
                    <div className="d-flex align-start">
                      <RangeWithIcons
                        value={product.ratingAvg}
                        max={5}
                        min={0.5}
                        step={0.1}
                        borderColor={"#ffd151"}
                        color={"#ffd151"}
                        starWidth={"40px"}
                      />
                    </div>
                  </div>
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
                    <col style={{ width: "384px" }} />
                    <col style={{ width: "134px" }} />
                  </colgroup>

                  <tbody>
                    <tr>
                      <td colSpan="2">{product.name}</td>
                    </tr>
                    <tr style={{ height: "57px" }}>
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
                        {useMoney(
                          Math.ceil(
                            product.price *
                              (1 - 0.01 * product.discount) *
                              quantity,
                          ),
                        )}
                        원
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
                  <span>
                    {useMoney(
                      Math.ceil(
                        product.price *
                          (1 - 0.01 * product.discount) *
                          quantity,
                      ),
                    )}
                    원
                  </span>
                  ({quantity}
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
                {isLogin() ? (
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
                ) : (
                  <div
                    className="buyBtn"
                    onClick={() => {
                      alert("로그인이 필요한 서비스입니다.");
                      document.location.href = "/member/login";
                    }}
                  >
                    바로 구매
                  </div>
                )}
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
                <Nutri nutrition={product.nutrition} />
              ) : reviews.length > 0 ? (
                <Review
                  reviews={reviews}
                  page={page}
                  setPage={setPage}
                  lastPage={lastPage}
                />
              ) : (
                <div className="noReview">
                  <p>아직 작성된 후기가 없습니다.</p>
                </div>
              )}
            </div>
          </div>
        </div>
      )}
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 57px auto 0;
            width: 1240px;

            .detailTop {
              display: flex;
              justify-content: space-between;

              .detailImg {
                width: 600px;
                height: 600px;
                text-align: center;
                border-radius: 30px;
                background-color: ${ThemeGray};

                img {
                  width: 600px;
                  height: 600px;
                }
              }

              .detailEtc {
                width: 560px;

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
                    width: 490px;
                    font-size: 12px;
                    color: red;
                    font-weight: 700;
                  }
                }

                .totalProducts {
                  background-color: ${ThemeGray};
                  margin-bottom: 5px;
                  padding: 20px 15px 15px;
                  border-radius: 8px;

                  input[type="button"] {
                    background-color: #fff;
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
                    width: 252px;
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
                    width: 252px;
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

          .noReview {
            text-align: center;
            margin: 2rem 0 3rem 0;
          }

          .d-flex {
            display: flex;
            justify-content: center;
          }

          .align-start {
            align-items: start;
          }

          .mt-3 {
            margin-top: 1rem;
            width: fit-content;
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 57px auto 0;
            width: 1240px;

            .detailTop {
              display: flex;
              justify-content: space-between;

              .detailImg {
                width: 600px;
                height: 600px;
                text-align: center;
                border-radius: 30px;
                background-color: ${ThemeGray};

                img {
                  width: 600px;
                  height: 600px;
                }
              }

              .detailEtc {
                width: 560px;

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
                    width: 490px;
                    font-size: 12px;
                    color: red;
                    font-weight: 700;
                  }
                }

                .totalProducts {
                  background-color: ${ThemeGray};
                  margin-bottom: 5px;
                  padding: 20px 15px 15px;
                  border-radius: 8px;

                  input[type="button"] {
                    background-color: #fff;
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
                    width: 252px;
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
                    width: 252px;
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

          .noReview {
            text-align: center;
            margin: 2rem 0 3rem 0;
          }

          .d-flex {
            display: flex;
            justify-content: center;
          }

          .align-start {
            align-items: start;
          }

          .mt-3 {
            margin-top: 1rem;
            width: fit-content;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 20px auto 0;
            width: 450px;
            height: 1400px;

            .detailTop {
              display: flex;
              justify-content: space-between;

              .detailImg {
                width: 200px;
                height: 200px;
                text-align: center;
                border-radius: 30px;
                background-color: ${ThemeGray};

                img {
                  width: 200px;
                  height: 200px;
                }
              }

              .detailEtc {
                width: 230px;

                .headingArea {
                  font-size: 20px;
                  line-height: 25px;
                  color: ${NBlack};
                  font-weight: 700;

                  .headingAreaName {
                    margin-top: 0;
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
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 350px;
                  font-size: 16px;
                  display: flex;

                  p {
                    width: 120px;
                  }
                }

                .coupon {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 400px;

                  font-size: 16px;
                  display: flex;
                  align-items: center;

                  p {
                    width: 300px;
                    font-size: 12px;
                    color: red;
                    font-weight: 700;
                  }
                }

                .totalProducts {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 480px;

                  background-color: ${ThemeGray};
                  margin-bottom: 5px;
                  padding: 20px 15px 15px;
                  border-radius: 8px;

                  input[type="button"] {
                    background-color: #fff;
                    border: 1px solid ${NGray};
                    border-radius: 50%;
                  }
                }

                .totalPrice {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 600px;
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
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 700px;

                  display: flex;
                  padding: 30px 0 10x;
                  justify-content: space-around;

                  .cartBtn {
                    width: 225px;
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
                    width: 225px;
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
              position: absolute;
              top: 770px;
              padding-top: 40px;

              .tabMenu {
                margin: 0 auto 30px;
                border-bottom: 2px solid ${NLightGray};

                ul {
                  margin: 0 auto;
                  padding: 0;
                  display: flex;
                  justify-content: center;
                  text-align: center;
                  list-style: none;

                  li {
                    position: relative;
                    display: inline-block;
                    width: 150px;
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

          .noReview {
            text-align: center;
            margin: 2rem 0 3rem 0;
          }

          .d-flex {
            display: flex;
            justify-content: center;
          }

          .align-start {
            align-items: start;
          }

          .mt-3 {
            margin-top: 1rem;
            width: fit-content;
          }
        }
      `}</style>
    </>
  );
}
