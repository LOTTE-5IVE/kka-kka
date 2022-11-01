import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { PostHApi } from "../../../apis/Apis";
import Title from "../../../components/common/Title";
import { useGetToken } from "../../../hooks/useGetToken";
import Swal from "sweetalert2";
import { isLogin } from "../../../hooks/isLogin";
import { AdminButton } from "../../../components/common/Button/AdminButton";
import { CouponDown } from "../../../components/coupon/CouponDown";
import { CouponModal } from "../../../components/coupon/CouponModal";
import Info from "../../../components/product/productDetail/Info";
import Nutri from "../../../components/product/productDetail/Nutri";
import Review from "../../../components/product/productDetail/Review";
import { useMoney } from "../../../hooks/useMoney";
import { NBlack, NGray, NLightGray } from "../../../typings/NormalColor";
import {
  ThemeBlue,
  ThemeGray,
  ThemePink,
  ThemeRed,
} from "../../../typings/ThemeColor";
import RangeWithIcons from "../../../components/mypage/review/RangeWithIcons";
import { useNumberCheck } from "../../../hooks/useNumberCheck";

export default function ProductDetail() {
  const router = useRouter();
  const productId = router.query.id;
  const [token, setToken] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [tab, setTab] = useState("info");
  const [modal, setModal] = useState(false);
  const [product, setProduct] = useState({});
  const [reviews, setReviews] = useState([]);
  const [page, setPage] = useState(1);
  const [lastPage, setLastPage] = useState();

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
        imageUrl: `${product.image_url}`,
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
          );

          Swal.fire("", "장바구니에 성공적으로 담겼습니다.", "success");
        }
      });
    }
  };

  const getItem = async () => {
    if (productId) {
      await axios.get(`/api/products/${productId}`).then((res) => {
        setProduct(res.data);
      });
    }
  };

  const getReview = async () => {
    if (productId) {
      await axios
        .get(`/api/reviews?product=${productId}&page=${page}`)
        .then((res) => {
          setReviews(res.data.data);
          setPage(res.data.pageInfo.currentPage);
          setLastPage(res.data.pageInfo.lastPage);
        });
    }
  };

  useEffect(() => {
    if (!router.isReady) {
      return;
    }

    getReview();
  }, [router.isReady]);

  useEffect(() => {
    setToken(useGetToken());
  }, [token, quantity]);

  useEffect(() => {
    getItem();
  }, [productId]);

  useEffect(() => {
    getReview();
  }, [page]);

  return (
    <>
      <Title title="상품상세" />
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

                          <input
                            type="text"
                            onChange={(e) => {
                              if (!useNumberCheck(e.target.value)) {
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
            margin: 3vw auto 0;
            width: 85vw;

            .detailTop {
              display: flex;
              justify-content: space-between;

              .detailImg {
                width: 33vw;
                height: 33vw;
                text-align: center;
                border-radius: 2vw;
                background-color: ${ThemeGray};

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
                  color: ${NBlack};
                  font-weight: 700;

                  .headingAreaName {
                    margin-top: 1.21vw;
                  }

                  .headingDescription {
                    font-size: 3vw;
                    margin: 1.5vw 0 0;
                    height: 12vw;
                    padding-bottom: 1.58vw;
                    border-bottom: 1px solid ${NLightGray};

                    p {
                      margin: 0;

                      span {
                        font-size: 1.26vw;
                        color: ${NGray};
                        text-decoration: line-through;
                      }
                    }
                  }
                }

                .delivery {
                  margin: 1.05vw 0 0.53vw;
                  font-size: 0.84vw;
                  display: flex;

                  p {
                    width: 15vw;
                  }
                }

                .coupon {
                  margin: 1.05vw 0 0.53vw;
                  font-size: 0.84vw;
                  display: flex;
                  align-items: center;

                  p {
                    width: 25.79vw;
                    font-size: 1vw;
                    color: red;
                    font-weight: 700;
                  }
                }

                .totalProducts {
                  background-color: ${ThemeGray};
                  margin-bottom: 0.26vw;
                  padding: 1.05vw 0.79vw 0.79vw;
                  border-radius: 0.42vw;
                  font-size: 2.5vw;

                  input[type="button"] {
                    background-color: #fff;
                    border: 1px solid ${NGray};
                    border-radius: 50%;
                  }
                }

                .totalPrice {
                  padding: 1.58vw 0 0.53vw;
                  display: flex;
                  justify-content: space-between;
                  align-items: center;

                  strong {
                    font-size: 3vw;
                    color: ${NBlack};
                    font-weight: 500;
                  }

                  p {
                    float: right;
                    line-height: 1.74vw;
                    font-size: 3vw;
                    color: ${NBlack};
                    font-weight: 500;

                    span {
                      font-size: 5vw;
                      color: ${ThemeRed};
                      font-weight: 700;
                    }
                  }
                }

                .btn {
                  display: flex;
                  padding: 1.58vw 0 0.53vw;
                  justify-content: space-around;

                  .cartBtn {
                    width: 20vw;
                    margin-top: 0.26vw;
                    background: ${ThemePink};
                    color: #fff;
                    border: 1px solid ${ThemePink};
                    text-align: center;
                    height: 9vw;
                    line-height: 9vw;
                    font-size: 3vw;
                    vertical-align: middle;
                    padding: 0;
                    cursor: pointer;
                  }

                  .buyBtn {
                    width: 20vw;
                    margin-top: 0.26vw;
                    background: ${ThemeBlue};
                    color: #fff;
                    border: 1px solid ${ThemeBlue};
                    text-align: center;
                    height: 9vw;
                    line-height: 9vw;
                    font-size: 3vw;
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
              padding-top: 2.11vw;

              .tabMenu {
                margin: 0 auto 4vw;
                border-bottom: 2px solid ${NLightGray};

                ul {
                  padding: 0;
                  margin: 0 auto;
                  display: flex;
                  justify-content: center;
                  text-align: center;
                  list-style: none;

                  li {
                    position: relative;
                    display: inline-block;
                    width: 27vw;
                    height: 9vw;
                    line-height: 9vw;

                    a {
                      display: block;
                      font-size: 3.5vw;
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
            margin-top: 3vw;
            width: fit-content;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .ProductDetailLWrapper {
            margin: 20px auto 0;
            width: 450px;
            height: 1440px;

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
                  top: 370px;
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
                  top: 420px;

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

export async function getServerSideProps(context) {
  return {
    props: {},
  };
}
