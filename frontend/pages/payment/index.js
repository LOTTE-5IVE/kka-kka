import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { PostHApi } from "../../apis/Apis";
import { AdminButton } from "../../components/common/Button/AdminButton";
import ButtonComp from "../../components/common/Button/ButtonComp";
import DaumPost from "../../components/payment/DaumPost";
import { useGetToken } from "../../hooks/useGetToken";
import { useMoney } from "../../hooks/useMoney";
import { NGray } from "../../typings/NormalColor";

export default function payment() {
  const [token, setToken] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [addr1, setAddr1] = useState("");
  const [addr2, setAddr2] = useState("");
  const [popup, setPopup] = useState(false);
  const [Selected, setSelected] = useState("");
  const [modal, setModal] = useState(false);
  const [orderItems, setOrderItems] = useState([]);
  const [buyItem, setBuyItem] = useState();
  const [buyQuantity, setBuyQuantity] = useState();

  const router = useRouter();

  useEffect(() => {
    if (!router.isReady) {
      return;
    }
    setToken(useGetToken());

    if (router.query.orderItems) {
      console.log("orderItems");
      setOrderItems(JSON.parse(router.query.orderItems));
    }

    if (router.query.buyItem) {
      setBuyItem(JSON.parse(router.query.buyItem));
      setBuyQuantity(router.query.quantity);
    }
  }, [router.isReady]);

  const orderItem = async () => {
    const arr = [];

    orderItems?.map((x) => {
      return arr.push({
        productId: x.productId,
        quantity: x.quantity,
      });
    });

    if (buyItem) {
      arr.push({ productId: buyItem.id, quantity: Number(buyQuantity) });
    }

    PostHApi("/api/orders", { productOrders: arr }, token).then((res) => {
      if (res) {
        console.log(res);
        alert("결제되었습니다.");
      }
    });
  };

  function modalHandler() {
    setModal(false);
  }

  function popupHandler() {
    setPopup(!popup);
  }

  function zipcodeHandler(zipcode) {
    setZipcode(zipcode);
  }

  function addr1Handler(addr1) {
    setAddr1(addr1);
  }

  const handleSelect = (e) => {
    setSelected(e.target.value);
  };

  return (
    <>
      <div className="wrapper">
        <div className="title">
          <h2>주문/결제</h2>
        </div>
        <div>
          <div className="tableTitle orderListTitle">주문상품</div>
          <div className="orderList">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "30%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "5%" }} />
              </colgroup>
              <tbody>
                {buyItem && (
                  <tr>
                    <td>
                      <img width="96px" src={buyItem.image_url} />
                    </td>
                    <td>{buyItem.name}</td>
                    <td>x{buyQuantity}</td>
                    <td>{useMoney(buyItem.price * buyQuantity)}원</td>
                    <td>
                      <div
                        onClick={() => {
                          setModal(true);
                        }}
                      >
                        <AdminButton
                          context="쿠폰적용"
                          color="red"
                          width="70px"
                        />
                      </div>
                      {modal && (
                        <CouponModal>
                          <div>
                            <CouponDown modalHandler={modalHandler} />
                          </div>
                        </CouponModal>
                      )}
                    </td>
                  </tr>
                )}

                {orderItems?.map((product, index) => {
                  return (
                    <tr key={index}>
                      <td>
                        <img width="96px" src={product.imageUrl} />
                      </td>
                      <td>{product.productName}</td>
                      <td>x{product.quantity}</td>
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
                      <td>
                        <div
                          onClick={() => {
                            setModal(true);
                          }}
                        >
                          <AdminButton
                            context="쿠폰적용"
                            color="red"
                            width="70px"
                          />
                        </div>
                        {modal && (
                          <CouponModal>
                            <div>
                              <CouponDown modalHandler={modalHandler} />
                            </div>
                          </CouponModal>
                        )}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>

        <div>
          <div className="tableTitle orderInfoTitle">주문자정보</div>
          <div className="tableContents orderInfo">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "80%" }} />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">주문자</th>
                  <td>
                    <input
                      id="oname"
                      className="inputTypeText"
                      placeholder=""
                      size="15"
                      defaultValue=""
                      type="text"
                    />
                  </td>
                </tr>
                <tr>
                  {/* https://choiiis.github.io/web/toy-project-sign-up-and-in-page-2/ */}
                  <th scope="row">이메일</th>
                  <td>
                    <input
                      id="oemail1"
                      className="mailId"
                      defaultValue=""
                      type="text"
                    />
                    @{" "}
                    <span className="mailAddress">
                      <span className="directInput ec-compact-etc">
                        <input
                          id="oemail2"
                          placeholder="직접입력"
                          defaultValue={Selected}
                          type="text"
                        />
                      </span>
                      <select
                        id="oemail3"
                        onChange={handleSelect}
                        defaultValue={"DEFAULT"}
                      >
                        <option value="DEFAULT" disabled>
                          -이메일 선택-
                        </option>
                        <option value="naver.com">naver.com</option>
                        <option value="daum.net">daum.net</option>
                        <option value="nate.com">nate.com</option>
                        <option value="hotmail.com">hotmail.com</option>
                        <option value="yahoo.com">yahoo.com</option>
                        <option value="empas.com">empas.com</option>
                        <option value="korea.com">korea.com</option>
                        <option value="dreamwiz.com">dreamwiz.com</option>
                        <option value="gmail.com">gmail.com</option>
                        <option value="etc">직접입력</option>
                      </select>
                    </span>
                  </td>
                </tr>
                <tr>
                  <th scope="row">휴대전화</th>
                  <td>
                    <select id="ophone1_1" defaultValue={"010"}>
                      <option value="02">02</option>
                      <option value="031">031</option>
                      <option value="032">032</option>
                      <option value="033">033</option>
                      <option value="041">041</option>
                      <option value="042">042</option>
                      <option value="043">043</option>
                      <option value="044">044</option>
                      <option value="051">051</option>
                      <option value="052">052</option>
                      <option value="053">053</option>
                      <option value="054">054</option>
                      <option value="055">055</option>
                      <option value="061">061</option>
                      <option value="062">062</option>
                      <option value="063">063</option>
                      <option value="064">064</option>
                      <option value="070">070</option>
                      <option value="010">010</option>
                      <option value="011">011</option>
                      <option value="016">016</option>
                      <option value="017">017</option>
                      <option value="018">018</option>
                      <option value="019">019</option>
                    </select>
                    -
                    <input
                      id="ophone1_2"
                      maxLength="4"
                      size="4"
                      defaultValue=""
                      type="text"
                    />
                    -
                    <input
                      id="ophone1_3"
                      maxLength="4"
                      size="4"
                      defaultValue=""
                      type="text"
                    />
                  </td>
                </tr>
                <tr>
                  <th scope="row" rowSpan="3">
                    주소
                  </th>
                  <td>
                    <div className="address_search">
                      <input
                        id="ozipcode1"
                        name="ozipcode1"
                        placeholder="우편번호"
                        className="inputTypeText"
                        type="text"
                        maxLength="14"
                        readOnly
                        defaultValue={zipcode ? zipcode : ""}
                      />
                      <button
                        onClick={() => {
                          popupHandler();
                        }}
                      >
                        우편번호 찾기
                      </button>
                      {popup && (
                        <DaumPost
                          zipcodeHandler={zipcodeHandler}
                          addrHandler={addr1Handler}
                        />
                      )}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <input
                      id="oaddr1"
                      name="oaddr1"
                      placeholder="기본주소"
                      className="inputTypeText"
                      type="text"
                      size="60"
                      maxLength="100"
                      readOnly
                      defaultValue={addr1 ? addr1 : ""}
                    />
                  </td>
                </tr>
                <tr>
                  <td>
                    <input
                      id="oaddr1"
                      name="oaddr1"
                      placeholder="상세주소"
                      className="inputTypeText"
                      type="text"
                      size="60"
                      maxLength="100"
                      readOnly
                      value={addr2}
                    />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div>
          <div className="tableTitle cpTitle">쿠폰/포인트</div>
          <div className="tableContents cp">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "80%" }} />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">쿠폰 할인</th>
                  <td>0원</td>
                </tr>
                <tr>
                  <th scope="row">적용금액</th>
                  <td>-0원</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div>
          <div className="tableTitle payInfoTitle">결제정보</div>
          <div className="tableContents payInfo">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "80%" }} />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">주문상품</th>
                  <td>
                    {useMoney(orderItems.reduce((prev, cur) => prev + (cur.price * cur.quantity), 0))} 원
                  </td>
                </tr>
                <tr>
                  <th scope="row">할인/부가결제</th>
                  <td>
                    - {useMoney(orderItems.reduce((prev, cur) => prev + Math.ceil(cur.price * 0.01 * cur.productDiscount * cur.quantity), 0)) || 0} 원
                  </td>
                </tr>
                <tr>
                  <th scope="row">배송비</th>
                  <td>0 원</td>
                </tr>
                <tr>
                  <th scope="row">결제금액</th>
                  <td>
                    {useMoney(
                      (orderItems.reduce((prev, cur) => prev + (cur.price * cur.quantity), 0)
                        - (orderItems.reduce((prev, cur) => prev + Math.ceil(cur.price * 0.01 * cur.productDiscount * cur.quantity), 0)))
                    )} 원
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div
          className="payBtn"
          onClick={() => {
            orderItem();
          }}
          style={{ cursor: "pointer" }}
        >
          <ButtonComp context="결제하기" />
        </div>
      </div>

      <style jsx>{`
        .wrapper {
          width: 70%;
          margin: 0 auto;

          .title {
            text-align: center;

            h2 {
              padding: 0;
              color: #3a3a3a;
              font-size: 36px;
              font-weight: 700;
              line-height: 1;
            }
          }

          .orderList {
            margin-bottom: 10%;
            border-bottom: 2px solid;
            table {
              border-collapse: collapse;
              margin: 0 auto;
              width: 98%;

              tr:not(:last-child) {
                height: 2vw;
                border-bottom: 1.5px solid #d0cfcf;
              }

              td {
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

          .tableTitle {
            font-size: 20px;
            font-weight: 500;
            line-height: 2;
            border-bottom: 2px solid;
          }

          .tableContents {
            margin-bottom: 10%;

            table {
              border-collapse: collapse;
              margin: 0 auto;
              width: 98%;

              tr {
                height: 5vw;
                border-bottom: 1.5px solid #d0cfcf;
              }
            }
          }

          .cp {
            td {
              text-align: right;
            }

            tr:last-child {
              background-color: #f8f8f8;
            }
          }

          .payInfo {
            td {
              text-align: right;
            }

            tr:last-child {
              background-color: #f8f8f8;
            }
          }

          .payBtn {
            width: 20%;
            margin: 0 auto 50px;
          }
        }
      `}</style>
    </>
  );
}
