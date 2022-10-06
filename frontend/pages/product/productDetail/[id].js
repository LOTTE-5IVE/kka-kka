import axios from "axios";
import { useRouter } from "next/router";
import { useQuery } from "react-query";

export default function productDetail() {
  const router = useRouter();
  const productId = router.query.id;

  const { isLoading, data } = useQuery("products", () => {
    return axios.get(`http://localhost:4000/products?id=${productId}`);
  });

  if (isLoading) {
    return <h2>Loading...</h2>;
  }

  return (
    <>
      {data?.data.map((product) => {
        return (
          <div className="wrapper">
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
                            <input type="button" value="-" />
                            <input
                              id="quantity"
                              name="quantity_opt[]"
                              value="1"
                              type="text"
                            />
                            <input type="button" value="+" />
                          </span>
                        </td>
                        <td style={{ textAlign: "right" }}>
                          {product.price}원
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
                    <span>{product.price}원</span>(1개)
                  </p>
                </div>
                <div className="btn">
                  <div className="cartBtn">장바구니</div>
                  <div className="buyBtn">바로 구매</div>
                </div>
              </div>
            </div>
            <div className="detailBottom">
              <div className="tabMenu">
                <ul>
                  <li>
                    <a>상품정보</a>
                  </li>
                  <li>
                    <a>영양정보</a>
                  </li>
                  <li>
                    <a>상품후기</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        );
      })}
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
              .totalProducts {
                background-color: #f8f8f8;
                margin-bottom: 5px;
                padding: 20px 15px 15px;
                border-radius: 8px;

                input[type="text"] {
                  width: 44px;
                  height: 23px;
                  padding: 0 5px;
                  border: 0;
                  font-weight: 400;
                  font-size: 16px;
                  color: #1a1a1a;
                  line-height: 29px;
                  text-align: center;
                  background: 0 0;
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
              }
            }
          }

          .detailBottom {
            padding-top: 40px;
            .tabMenu {
              margin: 0 auto 60px;
              border-bottom: 1px solid #ddd;

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
