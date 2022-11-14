import axios from "axios";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { GetHApi, PostHApi } from "../../../apis/Apis";
import Title from "../../../components/common/Title";
import Swal from "sweetalert2";
import { isLogin } from "../../../hooks/isLogin";
import { AdminButton } from "../../../components/common/Button/AdminButton";
import { CouponDown } from "../../../components/coupon/CouponDown";
import { CouponModal } from "../../../components/coupon/CouponModal";
import Info from "../../../components/product/productDetail/Info";
import Nutri from "../../../components/product/productDetail/Nutri";
import Review from "../../../components/product/productDetail/Review";
import RecommendList
  from "../../../components/product/productDetail/RecommendList";
import { commaMoney } from "../../../hooks/commaMoney";
import { NBlack, NGray, NLightGray } from "../../../typings/NormalColor";
import {
  ThemeBlue,
  ThemeGray,
  ThemePink,
  ThemeRed,
} from "../../../typings/ThemeColor";
import RangeWithIcons from "../../../components/mypage/review/RangeWithIcons";
import { isNumber } from "../../../hooks/isNumber";
import { useContext } from "react";
import { TokenContext } from "../../../context/TokenContext";
import { CartCntContext } from "../../../context/CartCntContext";

export default function ProductDetail() {
  const router = useRouter();
  const productId = router.query.id;
  const [quantity, setQuantity] = useState(1);
  const [tab, setTab] = useState("info");
  const [modal, setModal] = useState(false);
  const [product, setProduct] = useState({});
  const [reviewCount, setReviewCount] = useState(0);
  const { token, setToken } = useContext(TokenContext);
  const { cartCnt, setCartCnt } = useContext(CartCntContext);

  const buyQuery = () => {
    product.quantity = quantity;

    console.log("JSON.stringify(product): ", JSON.stringify([product]));

    router.push(
      {
        pathname: `/payment`,
        query: { orderItems: JSON.stringify([product]) },
      },
      `/payment`,
    );
  };

  const handleQuantity = (type) => {
    if (type !== "minus" || quantity > 1) {
      type === "plus"
        ? quantity + 1 > product.stock
          ? alert("수량 한도를 초과했습니다.")
          : setQuantity(quantity + 1)
        : setQuantity(quantity - 1);
    }
  };

  function handleModal(bool) {
    setModal(bool);
  }

  function handleTab(text) {
    setTab(text);
  }

  const addCartItem = async (product, quantity) => {
    if (!isLogin()) {
      alert("로그인이 필요한 서비스입니다.");
      document.location.href = "/member/login";
    } else {
      Swal.fire({
        title: "장바구니에 담으시겠습니까?",
        html: `${product.name}` + "<br/>" + `수량 : ${quantity}개`,
        imageUrl: `${product.imageUrl}`,
        imageHeight: 300,
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "장바구니 담기",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          PostHApi(
            "/api/carts",
            {
              productId: product.id,
              quantity: quantity,
            },
            token,
          ).then((res) => {
            getCartCount();
          });

          Swal.fire("", "장바구니에 성공적으로 담겼습니다.", "success");
        }
      });
    }
  };

  const getCartCount = async () => {
    await GetHApi("/api/members/me/carts/all", token).then((res) => {
      setCartCnt(res.cartCount);
    });
  };

  const getItem = async () => {
    if (productId) {
      await axios.get(`/api/products/${productId}`).then((res) => {
        setProduct(res.data);
      });
    }
  };

  const getReviewCount = async () => {
    if (productId) {
      await axios.get(`/api/reviews/${productId}/all`)
      .then((res) => {
        console.log(res.data.reviewCount)
        setReviewCount(res.data.reviewCount);
        console.log(reviewCount)
      });
    }
  }

  useEffect(() => {
    getItem();
    getReviewCount();
  }, [productId]);

  return (
    <>
      <Title title="상품상세" />
      {product && (
        <div className="ProductDetailLWrapper" key={product.id}>
          <div className="detailTop">
            <div className="detailImg">
              <img src={`${product.imageUrl}`} />
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
                        {commaMoney(
                          Math.ceil(
                            product.price * (1 - 0.01 * product.discount),
                          ),
                        )}
                        원 <span>{commaMoney(product.price)}원</span>
                      </p>
                    </>
                  ) : (
                    <>
                      <p>{commaMoney(product.price)}원</p>
                    </>
                  )}
                  <div className="mt-3">
                    <div className="review-box d-flex align-start">
                      <RangeWithIcons
                        value={product.ratingAvg}
                        max={5}
                        min={0.5}
                        step={0.1}
                        borderColor={"#ffd151"}
                        color={"#ffd151"}
                        starWidth={"40px"}
                      />
                      <div className="reviewCnt">
                        ({(product.ratingAvg)?.toFixed(1)}, {commaMoney(reviewCount) || 0}개)
                      </div>
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
                      <CouponDown handleModal={handleModal} product={product} />
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
                            style={{ marginRight: "15px", cursor: "pointer" }}
                          />

                          <input
                            type="text"
                            onChange={(e) => {
                              if (!isNumber(e.target.value)) {
                                alert("숫자만 입력하세요.");
                                setQuantity(1);
                                return;
                              }

                              Number(e.target.value) > product.stock
                                ? alert("수량 한도를 초과했습니다.")
                                : setQuantity(Number(e.target.value));
                            }}
                            onBlur={(e) => {
                              if (Number(e.target.value) < 1) {
                                alert("최소 수량은 1개입니다.");
                                setQuantity(1);
                              }
                            }}
                            size="1"
                            value={quantity}
                            style={{ textAlign: "center" }}
                          />

                          <input
                            type="button"
                            onClick={() => handleQuantity("plus")}
                            value="+"
                            style={{ marginLeft: "15px", cursor: "pointer" }}
                          />
                        </span>
                      </td>
                      <td style={{ textAlign: "right" }}>
                        {commaMoney(
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
                    {commaMoney(
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
                  <div className="buyBtn" onClick={buyQuery}>
                    바로 구매
                  </div>
                ) : (
                  // <div
                  //   className="buyBtn"
                  //   onClick={() => {
                  //     console.log("productDetailbefore: ", product);
                  //     product.quantity = quantity;

                  //     console.log("productDetail: ", product);
                  //   }}
                  // >
                  //   바로 구매
                  // </div>
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
          <div className="detailMiddle">
            <RecommendList />
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
                <Info detailImageUrl={product.detailImageUrl} />
              ) : tab == "nutri" ? (
                <Nutri nutrition={product.nutrition} />
              ) : (
                <Review productId={productId} />
              )}
            </div>
          </div>
        </div>
      )}
      <style jsx>{`
        .ProductDetailLWrapper {
          .detailTop {
            display: flex;
            justify-content: space-between;

            .detailImg {
              text-align: center;
              background-color: ${ThemeGray};
            }

            .detailEtc {
              .headingArea {
                color: ${NBlack};
                font-weight: 700;

                .headingDescription {
                  border-bottom: 1px solid ${NLightGray};

                  p {
                    margin: 0;

                    span {
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }
                }
              }

              .delivery {
                display: flex;
              }

              .coupon {
                display: flex;
                align-items: center;

                p {
                  color: red;
                  font-weight: 700;
                }
              }

              .totalProducts {
                background-color: ${ThemeGray};

                input[type="button"] {
                  background-color: #fff;
                  border: 1px solid ${NGray};
                  border-radius: 50%;
                }
              }

              .totalPrice {
                display: flex;
                justify-content: space-between;
                align-items: center;

                strong {
                  color: ${NBlack};
                  font-weight: 500;
                }

                p {
                  float: right;
                  color: ${NBlack};
                  font-weight: 500;

                  span {
                    color: ${ThemeRed};
                    font-weight: 700;
                  }
                }
              }

              .btn {
                display: flex;
                justify-content: space-around;

                .cartBtn {
                  background: ${ThemePink};
                  color: #fff;
                  border: 1px solid ${ThemePink};
                  text-align: center;
                  vertical-align: middle;
                  padding: 0;
                  cursor: pointer;
                }

                .buyBtn {
                  background: ${ThemeBlue};
                  color: #fff;
                  border: 1px solid ${ThemeBlue};
                  text-align: center;
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
              
              .review-box {
                align-items: flex-end;
              }
              
              .reviewCnt {
                font-size: 1rem;
                font-weight: 400;
                color: ${NGray};
                width: 100%;
                margin-left: 0.6rem;
              }
            }
          }

          .detailBottom {
            .tabMenu {
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

                  a {
                    display: block;
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

        .d-flex {
          display: flex;
          justify-content: center;
        }

        .align-start {
          align-items: start;
        }

        .mt-3 {
          width: fit-content;
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 57px auto 0;
            width: 1240px;

            .detailTop {
              .detailImg {
                width: 600px;
                height: 600px;
                border-radius: 30px;

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

                  .headingAreaName {
                    margin-top: 23px;
                  }

                  .headingDescription {
                    font-size: 32px;
                    margin: 28px 0 0;
                    height: 150px;
                    padding-bottom: 30px;

                    p {
                      span {
                        font-size: 24px;
                      }
                    }
                  }
                }

                .delivery {
                  margin: 20px 0 10px;
                  font-size: 16px;

                  p {
                    width: 120px;
                  }
                }

                .coupon {
                  margin: 20px 0 10px;
                  font-size: 16px;

                  p {
                    width: 490px;
                    font-size: 12px;
                  }
                }

                .totalProducts {
                  margin-bottom: 5px;
                  padding: 20px 15px 15px;
                  border-radius: 8px;
                }

                .totalPrice {
                  padding: 30px 0 10px;

                  strong {
                    font-size: 18px;
                  }

                  p {
                    line-height: 33px;
                    font-size: 14px;

                    span {
                      font-size: 32px;
                    }
                  }
                }

                .btn {
                  padding: 30px 0 10x;

                  .cartBtn {
                    width: 252px;
                    margin-top: 5px;
                    height: 60px;
                    line-height: 60px;
                    font-size: 18px;
                  }

                  .buyBtn {
                    width: 252px;
                    margin-top: 5px;
                    height: 60px;
                    line-height: 60px;
                    font-size: 18px;
                  }
                }
              }
            }

            .detailBottom {
              padding-top: 40px;

              .tabMenu {
                margin: 0 auto 60px;

                ul {
                  li {
                    width: 280px;
                    height: 60px;
                    line-height: 60px;

                    a {
                      font-size: 18px;
                    }
                  }
                }
              }
            }
          }

          .mt-3 {
            margin-top: 1rem;
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 3vw auto 0;
            width: 85vw;

            .detailTop {
              .detailImg {
                width: 33vw;
                height: 33vw;
                border-radius: 2vw;

                img {
                  width: 33vw;
                  height: 33vw;
                }
              }

              .detailEtc {
                width: 40vw;

                .headingArea {
                  font-size: 2.5vw;
                  line-height: 2vw;

                  .headingAreaName {
                    margin-top: 1.21vw;
                  }

                  .headingDescription {
                    font-size: 3vw;
                    margin: 1.5vw 0 0;
                    height: 12vw;
                    padding-bottom: 1.58vw;

                    p {
                      margin: 2vw 0 0 0;

                      span {
                        font-size: 1.26vw;
                      }
                    }
                  }
                }

                .delivery {
                  margin: 1.05vw 0 0.53vw;
                  font-size: 0.84vw;

                  p {
                    width: 15vw;
                  }
                }

                .coupon {
                  margin: 1.05vw 0 0.53vw;
                  font-size: 0.84vw;

                  p {
                    width: 25.79vw;
                    font-size: 1vw;
                  }
                }

                .totalProducts {
                  margin-bottom: 0.26vw;
                  padding: 1.05vw 0.79vw 0.79vw;
                  border-radius: 0.42vw;
                  font-size: 2.5vw;
                }

                .totalPrice {
                  padding: 1.58vw 0 0.53vw;

                  strong {
                    font-size: 3vw;
                  }

                  p {
                    line-height: 1.74vw;
                    font-size: 3vw;

                    span {
                      font-size: 5vw;
                    }
                  }
                }

                .btn {
                  padding: 1.58vw 0 0.53vw;

                  .cartBtn {
                    width: 20vw;
                    margin-top: 0.26vw;
                    height: 9vw;
                    line-height: 9vw;
                    font-size: 3vw;
                  }

                  .buyBtn {
                    width: 20vw;
                    margin-top: 0.26vw;
                    height: 9vw;
                    line-height: 9vw;
                    font-size: 3vw;
                  }
                }
                
                .reviewCnt {
                  font-size: 1vw;
                  margin-left: 0.2rem;
                }
              }
            }

            .detailBottom {
              padding-top: 2.11vw;

              .tabMenu {
                margin: 0 auto 4vw;

                ul {
                  padding: 0;

                  li {
                    width: 27vw;
                    height: 9vw;
                    line-height: 9vw;

                    a {
                      font-size: 3.5vw;
                    }
                  }
                }
              }
            }
          }

          .mt-3 {
            margin-top: 2vw;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 20px auto 0;
            width: 450px;
            height: 1440px;

            .detailTop {
              .detailImg {
                width: 200px;
                height: 200px;
                border-radius: 30px;

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

                  .headingAreaName {
                    margin-top: 0;
                  }

                  .headingDescription {
                    font-size: 32px;
                    margin: 28px 0 0;
                    height: 140px;
                    padding-bottom: 30px;

                    p {
                      margin: 15px 0 0;

                      span {
                        font-size: 24px;
                      }
                    }
                  }
                }

                .delivery {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 370px;
                  font-size: 16px;

                  p {
                    width: 120px;
                  }
                }

                .coupon {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 420px;
                  font-size: 16px;

                  p {
                    width: 300px;
                    font-size: 12px;
                  }
                }

                .totalProducts {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 480px;

                  margin-bottom: 5px;
                  padding: 20px 15px 15px;
                  border-radius: 8px;
                }

                .totalPrice {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 600px;
                  padding: 30px 0 10px;

                  strong {
                    font-size: 18px;
                  }

                  p {
                    line-height: 33px;
                    font-size: 14px;

                    span {
                      font-size: 32px;
                    }
                  }
                }

                .btn {
                  position: absolute;
                  width: 400px;
                  left: 40px;
                  top: 700px;

                  padding: 30px 0 10x;

                  .cartBtn {
                    width: 225px;
                    margin-top: 5px;
                    height: 60px;
                    line-height: 60px;
                    font-size: 18px;
                  }

                  .buyBtn {
                    width: 225px;
                    margin-top: 5px;
                    height: 60px;
                    line-height: 60px;
                    font-size: 18px;
                  }
                }
              }
            }
            
            .detailMiddle {
              position: absolute;
              top: 800px;
              width: 400px;
            }

            .detailBottom {
              position: absolute;
              top: 1150px;
              padding-top: 40px;

              .tabMenu {
                margin: 0 auto 30px;

                ul {
                  padding: 0;

                  li {
                    width: 150px;
                    height: 60px;
                    line-height: 60px;

                    a {
                      font-size: 18px;
                    }
                  }
                }
              }
            }
          }

          .mt-3 {
            margin-top: 1rem;
          }
        }
      `}</style>
    </>
  );
}

export async function getServerSideProps(context) {
  return {
    props: {},
  };
}
