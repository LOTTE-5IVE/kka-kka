import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/router";
import { useState } from "react";
import { useQuery } from "react-query";
import { AdminButton } from "../components/admin/AdminButton";
import Title from "../common/Title";
import { CouponDown } from "../coupon/CouponDown";
import { CouponModal } from "../coupon/CouponModal";
import Info from "../product/Info";
import Nutri from "../product/Nutri";
import Review from "../product/Review";

export default function productDetail() {
  const router = useRouter();
  const productId = router.query.id;

  const [quantity, setQuantity] = useState(1);
  const [tab, setTab] = useState("info");
  const [modal, setModal] = useState(false);
  const [product, setProduct] = useState({});
  const [pId, setPId] = useState();

  const handleQuantity = (type) => {
    if (type !== "minus" || quantity > 1) {
      type === "plus" ? setQuantity(quantity + 1) : setQuantity(quantity - 1);
    }
  };

  function modalHandler() {
    setModal(false);
  }

  const getItem = async () => {
    await axios.get(`http://localhost:9000/api/products/${pId}`).then((res) => {
      setProduct(res.data);
    });
  };

  useState(() => {
    console.log("pid:" + { pId });
    console.log("productId:" + { productId });
    console.log(`http://localhost:9000/api/products/${pId}`);
    setPId(productId);
    getItem();
  }, [product]);

  return (
    <>
      <Title title="상품상세" />

      {product && (
        <div className="wrapper" key={product.id}>
          <div className="detailTop">
            <div className="detailImg">
              <img src={`${product.imageUrl}`} />
            </div>

            <div className="detailEtc" key={product.id}>
              <div className="headingArea">
                <div className="headingAreaName">{product.name}</div>

                <div className="headingDescription">{product.price}</div>
              </div>
              <div className="delivery">
                <p>배송비</p> <p style={{ color: "#9a9a9a" }}>무료</p>
              </div>
              <div className="coupon">
                <p>고객님께만 드리는 쿠폰이 있어요</p>{" "}
                <div
                  onClick={() => {
                    setModal(true);
                  }}
                >
                  <AdminButton context="쿠폰받기" color="red" width="70px" />
                </div>
                {modal && (
                  <CouponModal>
                    <div
                      onClick={() => {
                        setModal(false);
                      }}
                    >
                      <CouponDown modalHandler={modalHandler} />
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
                        {product.price * quantity}원
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
                  <span>{product.price * quantity}원</span>({quantity}개)
                </p>
              </div>
              <div className="btn">
                <Link href="/member/cart">
                  <div className="cartBtn">장바구니</div>
                </Link>

                <div className="buyBtn">바로 구매</div>
              </div>
            </div>
          </div>
          <div className="detailBottom">
            <div className="tabMenu">
              <ul>
                <li onClick={() => setTab("info")}>
                  <a className={`${tab === "info" ? "active" : ""}`}>
                    상품정보
                  </a>
                </li>
                <li onClick={() => setTab("nutri")}>
                  <a className={`${tab === "nutri" ? "active" : ""}`}>
                    영양정보
                  </a>
                </li>
                <li onClick={() => setTab("review")}>
                  <a className={`${tab === "review" ? "active" : ""}`}>
                    상품후기
                  </a>
                </li>
              </ul>
            </div>
            <div className="descriptions">
              {tab == "info" ? (
                <Info />
              ) : tab == "nutri" ? (
                <Nutri />
              ) : (
                <Review />
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
              background-color: #f5f5f5;
              height: 28vw;

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
                color: #3a3a3a;
                font-weight: 700;
                .headingAreaName {
                  margin: 23px 0 50px;
                }

                .headingDescription {
                  font-size: 32px;
                  margin: 28px 0 0;
                  height: 70px;
                  padding-bottom: 30px;
                  border-bottom: 1px solid #e5e5e5;
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
                background-color: #f8f8f8;
                margin-bottom: 5px;
                padding: 20px 15px 15px;
                border-radius: 8px;

                input[type="button"] {
                  background-color: #fff;
                  border: 1px solid #c8c8c8;
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
                  color: #3a3a3a;
                  font-weight: 500;
                }

                p {
                  float: right;
                  line-height: 33px;
                  font-size: 14px;
                  color: #3a3a3a;
                  font-weight: 500;
                  span {
                    font-size: 32px;
                    color: #ff3d44;
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
                  background: #f2889b;
                  color: #fff;
                  border: 1px solid #f2889b;
                  text-align: center;
                  height: 60px;
                  line-height: 60px;
                  font-size: 18px;
                  vertical-align: middle;
                  padding: 0;
                }

                .buyBtn {
                  width: 45%;
                  margin-top: 5px;
                  background: #05c7f2;
                  color: #fff;
                  border: 1px solid #05c7f2;
                  text-align: center;
                  height: 60px;
                  line-height: 60px;
                  font-size: 18px;
                  padding: 0;
                }

                .cartBtn:hover {
                  background-color: #ffff;
                  color: #f2889b;
                  transition: 0.7s;
                }

                .buyBtn:hover {
                  background-color: #fff;
                  color: #05c7f2;
                  transition: 0.7s;
                }
              }
            }
          }

          .detailBottom {
            padding-top: 40px;
            .tabMenu {
              margin: 0 auto 60px;
              border-bottom: 2px solid #ddd;

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

                    color: #9a9a9a;
                    text-align: center;
                    cursor: pointer;
                  }

                  .active {
                    color: #000;
                    border-bottom: 2px solid red;
                  }

                  a:hover {
                    color: #3a3a3a;
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
