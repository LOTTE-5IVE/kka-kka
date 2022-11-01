import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { PostHApi } from "../../apis/Apis";
import { AdminButton } from "../../components/common/Button/AdminButton";
import ButtonComp from "../../components/common/Button/ButtonComp";
import Title from "../../components/common/Title";
import { CouponDown } from "../../components/coupon/CouponDown";
import { CouponModal } from "../../components/coupon/CouponModal";
import DaumPost from "../../components/payment/DaumPost";
import { useGetToken } from "../../hooks/useGetToken";
import { useLangCheck } from "../../hooks/useLangCheck";
import { useMemberInfo } from "../../hooks/useMemberInfo";
import { useMoney } from "../../hooks/useMoney";
import { useNumberCheck } from "../../hooks/useNumberCheck";
import { useTextCheck } from "../../hooks/useTextCheck";
import { NGray } from "../../typings/NormalColor";

export default function payment() {
  const [token, setToken] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [name, setName] = useState();
  const [email1, setEmail1] = useState();
  const [email2, setEmail2] = useState();
  const [phone1, setPhone1] = useState();
  const [phone2, setPhone2] = useState();
  const [phone3, setPhone3] = useState();
  const [addr1, setAddr1] = useState("");
  const [addr2, setAddr2] = useState("");
  const [popup, setPopup] = useState(false);
  const [modal, setModal] = useState(false);
  const [orderItems, setOrderItems] = useState([]);
  const [buyItem, setBuyItem] = useState();
  const [buyQuantity, setBuyQuantity] = useState();
  const [check, setCheck] = useState(false);

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

  useEffect(() => {
    if (!check) return;

    if (token !== "") {
      useMemberInfo(token).then((res) => {
        if (res) {
          console.log(res);
          setName(res.name);

          if (res.email) {
            let [e1, e2] = res.email.split("@");
            if (e1 && e2) {
              setEmail1(e1);
              setEmail2(e2);
            }
          }

          if (res.phone) {
            let [p1, p2, p3] = res.phone?.split("-");
            if (p1 && p2 && p3) {
              setPhone1(p1);
              setPhone2(p2);
              setPhone3(p3);
            }
          }

          if (res.address) {
            setAddr1(res.address);
          }
        }
      });
    }
  }, [check]);

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

    PostHApi(
      "/api/orders",
      {
        productOrders: arr,
        recipient: {
          name: name,
          email: email1 + "@" + email2,
          phone: phone1 + "-" + phone2 + "-" + phone3,
          address: addr1 + " " + addr2,
        },
      },
      token,
    ).then((res) => {});

    alert("결제되었습니다.");

    document.location.href = "/";
  };

  function handleModal() {
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

  const handleCheck = (e) => {
    setCheck(e.target.checked);
  };

  return (
    <>
      <Title title="주문/결제" />
      <div className="PaymentWrapper">
        <div className="title">
          <h2>주문/결제</h2>
        </div>
        <div>
          <div className="tableTitle orderListTitle">주문상품</div>
          <div className="orderList">
            <table>
              <colgroup>
                <col style={{ width: "15%" }} />
                <col style={{ width: "40%" }} />
                <col style={{ width: "15%" }} />
                <col style={{ width: "20%" }} />
                <col style={{ width: "10%" }} />
              </colgroup>
              <tbody>
                {buyItem && (
                  <tr>
                    <td>
                      <img src={buyItem.image_url} />
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
                            <CouponDown handleModal={handleModal} />
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
                        <img src={product.imageUrl} />
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
                              <CouponDown handleModal={handleModal} />
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
          <div className="tableTitle orderInfoTitle">
            배송정보{" "}
            <p>
              <input type="checkbox" checked={check} onChange={handleCheck} />{" "}
              <span>회원정보와 동일</span>
            </p>
          </div>
          <div className="tableContents orderInfo">
            <table>
              <colgroup>
                <col style={{ width: "15%" }} />
                <col style={{ width: "85%" }} />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">수령인</th>
                  <td>
                    <input
                      required
                      placeholder=""
                      size="15"
                      defaultValue={name}
                      type="text"
                      onChange={(e) => {
                        setCheck(false);

                        if (useLangCheck(e.target.value)) {
                          setName(e.target.value);
                        } else {
                          alert("한글 혹은 영문만 입력할 수 있습니다.");
                          e.target.value = "";
                        }
                      }}
                    />
                  </td>
                </tr>
                <tr>
                  <th scope="row">이메일</th>
                  <td>
                    <input
                      defaultValue={email1}
                      type="text"
                      onChange={(e) => {
                        setCheck(false);
                        if (useTextCheck(e.target.value)) {
                          setEmail1(e.target.value);
                        } else {
                          alert("특수문자는 입력할 수 없습니다.");
                          e.target.value = "";
                        }
                      }}
                    />
                    @{" "}
                    <span className="mailAddress">
                      <span className="directInput ec-compact-etc">
                        <input
                          placeholder="직접입력"
                          defaultValue={email2}
                          type="text"
                          onChange={(e) => {
                            setCheck(false);
                            if (useTextCheck(e.target.value)) {
                              setEmail2(e.target.value);
                            } else {
                              alert("특수문자는 입력할 수 없습니다.");
                              e.target.value = "";
                            }
                          }}
                        />
                      </span>
                      <select
                        onChange={(e) => {
                          setCheck(false);
                          setEmail2(e.target.value);
                        }}
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
                    <input
                      maxLength="3"
                      size="3"
                      defaultValue={phone1}
                      type="text"
                      onChange={(e) => {
                        setCheck(false);
                        if (useNumberCheck(e.target.value)) {
                          setPhone1(e.target.value);
                        } else {
                          alert("숫자만 입력할 수 있습니다.");
                          e.target.value = "";
                        }
                      }}
                    />
                    -
                    <input
                      maxLength="4"
                      size="4"
                      defaultValue={phone2}
                      type="text"
                      onChange={(e) => {
                        setCheck(false);
                        if (useNumberCheck(e.target.value)) {
                          setPhone2(e.target.value);
                        } else {
                          alert("숫자만 입력할 수 있습니다.");
                          e.target.value = "";
                        }
                      }}
                    />
                    -
                    <input
                      maxLength="4"
                      size="4"
                      defaultValue={phone3}
                      type="text"
                      onChange={(e) => {
                        setCheck(false);
                        if (useNumberCheck(e.target.value)) {
                          setPhone3(e.target.value);
                        } else {
                          alert("숫자만 입력할 수 있습니다.");
                          e.target.value = "";
                        }
                      }}
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
                        placeholder="우편번호"
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
                      placeholder="기본주소"
                      type="text"
                      size="60"
                      maxLength="100"
                      readOnly
                      defaultValue={addr1 ? addr1 : ""}
                      onChange={(e) => {
                        setAddr1(e.target.value);
                      }}
                    />
                  </td>
                </tr>
                <tr>
                  <td>
                    <input
                      placeholder="상세주소"
                      type="text"
                      size="60"
                      maxLength="100"
                      defaultValue={addr2 ? addr2 : ""}
                      onChange={(e) => {
                        setAddr2(e.target.value);
                      }}
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
                <col style={{ width: "15%" }} />
                <col style={{ width: "85%" }} />
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
                <col style={{ width: "15%" }} />
                <col style={{ width: "85%" }} />
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">주문상품</th>
                  <td>
                    {useMoney(
                      orderItems.reduce(
                        (prev, cur) => prev + cur.price * cur.quantity,
                        0,
                      ),
                    )}{" "}
                    원
                  </td>
                </tr>
                <tr>
                  <th scope="row">할인/부가결제</th>
                  <td>
                    -{" "}
                    {useMoney(
                      orderItems.reduce(
                        (prev, cur) =>
                          prev +
                          Math.ceil(
                            cur.price *
                              0.01 *
                              cur.productDiscount *
                              cur.quantity,
                          ),
                        0,
                      ),
                    ) || 0}{" "}
                    원
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
                      orderItems.reduce(
                        (prev, cur) => prev + cur.price * cur.quantity,
                        0,
                      ) -
                        orderItems.reduce(
                          (prev, cur) =>
                            prev +
                            Math.ceil(
                              cur.price *
                                0.01 *
                                cur.productDiscount *
                                cur.quantity,
                            ),
                          0,
                        ),
                    )}{" "}
                    원
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
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .PaymentWrapper {
            width: 1332px;
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
              margin-bottom: 133px;
              border-bottom: 2px solid;
              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 1305px;

                tr:not(:last-child) {
                  height: 103px;
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

                  img {
                    width: 96px;
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
              margin-bottom: 133px;

              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 1305px;

                tr {
                  height: 96px;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .orderInfoTitle {
              display: flex;
              align-items: center;
              p {
                margin: 0 0 0 30px;
                display: flex;
                justify-content: center;
                align-items: center;

                span {
                  font-size: 16px;
                }
              }
            }

            .orderInfo {
              font-size: 20px;

              input {
                margin: 0 10px;
                line-height: 40px;
                padding: 0 0 0 13px;
                border: 0 none;
                color: #3a3a3a;
                background: #fff;
                border-radius: 8px;
                font-size: 16px;
                border: 1px solid #000;
              }

              select {
                margin: 0 10px;
                height: 40px;
                border-radius: 8px;
                font-size: 16px;
                color: #3a3a3a;
                padding: 0 10px 0 13px;
                border: 1px solid #000;
              }

              button {
                margin: 0 10px;
                line-height: 40px;
                padding: 0 13px;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 16px;
                border: 1px solid #000;
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
              width: 200px;
              margin: 0 auto 50px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .PaymentWrapper {
            width: 100vw;
            margin: 0 auto;

            .title {
              text-align: center;

              h2 {
                padding: 0;
                color: #3a3a3a;
                font-size: 5vw;
                font-weight: 700;
                line-height: 1;
              }
            }

            .orderList {
              margin-bottom: 7vw;
              border-bottom: 2px solid;
              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 95vw;

                tr:not(:last-child) {
                  height: 5.53vw;
                  border-bottom: 1.5px solid #d0cfcf;
                }

                td {
                  p {
                    margin: 0;

                    span {
                      font-size: 1.5vw;
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }

                  img {
                    width: 96px;
                  }
                }
              }
            }

            .tableTitle {
              font-size: 3vw;
              font-weight: 500;
              line-height: 2;
              border-bottom: 2px solid;
            }

            .tableContents {
              margin-bottom: 7vw;

              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 95vw;

                tr {
                  height: 96px;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .orderInfoTitle {
              display: flex;
              align-items: center;
              p {
                margin: 0 0 0 20px;
                display: flex;
                justify-content: center;
                align-items: center;

                span {
                  font-size: 12px;
                }
              }
            }

            .orderInfo {
              font-size: 2vw;

              input {
                margin: 0 0.53vw;
                line-height: 30px;
                padding: 0 0 0 0.7vw;
                border: 0 none;
                color: #3a3a3a;
                background: #fff;
                border-radius: 8px;
                font-size: 1.5vw;
                border: 1px solid #000;
              }

              select {
                margin: 0 0.53vw;
                height: 30px;
                border-radius: 8px;
                font-size: 2vw;
                color: #3a3a3a;
                padding: 0 0.53vw 0 0.7vw;
                border: 1px solid #000;
              }

              button {
                margin: 0 0.53vw;
                line-height: 30px;
                padding: 0 0.7vw;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 2vw;
                border: 1px solid #000;
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
              width: 25vw;
              height: 10vw;
              margin: 0 auto 50px;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .PaymentWrapper {
            width: 460px;
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
              margin-bottom: 60px;
              border-bottom: 2px solid;
              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 450px;

                tr:not(:last-child) {
                  height: 70px;
                  border-bottom: 1.5px solid #d0cfcf;
                }

                td {
                  font-size: 12px;

                  p {
                    margin: 0;

                    span {
                      font-size: 14px;
                      color: ${NGray};
                      text-decoration: line-through;
                    }
                  }

                  img {
                    width: 64px;
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
              margin-bottom: 60px;

              table {
                border-collapse: collapse;
                margin: 0 auto;
                width: 450px;

                tr {
                  height: 80px;
                  border-bottom: 1.5px solid #d0cfcf;
                }
              }
            }

            .orderInfoTitle {
              display: flex;
              align-items: center;
              p {
                margin: 0 0 0 20px;
                display: flex;
                justify-content: center;
                align-items: center;

                span {
                  font-size: 12px;
                }
              }
            }

            .orderInfo {
              font-size: 15px;

              input {
                max-width: 300px;
                height: 30px;
                margin: 5px 10px;
                line-height: 40px;
                padding: 0 0 0 13px;
                border: 0 none;
                color: #3a3a3a;
                background: #fff;
                border-radius: 8px;
                font-size: 16px;
                border: 1px solid #000;
              }

              select {
                margin: 0 10px;
                width: 100px;
                height: 30px;
                border-radius: 8px;
                font-size: 16px;
                color: #3a3a3a;
                padding: 0 10px 0 13px;
                border: 1px solid #000;
              }

              button {
                margin: 0 10px;
                width: 100px;
                height: 30px;
                line-height: 30px;
                padding: 0 13px;
                border: 0 none;
                color: #3a3a3a;
                border-radius: 8px;
                font-size: 10px;
                font-weight: bold;
                border: 1px solid #000;
              }
            }

            .cp {
              font-size: 15px;
              td {
                text-align: right;
              }

              tr:last-child {
                background-color: #f8f8f8;
              }
            }

            .payInfo {
              font-size: 15px;
              td {
                text-align: right;
              }

              tr:last-child {
                background-color: #f8f8f8;
              }
            }

            .payBtn {
              width: 200px;
              margin: 0 auto 50px;
            }
          }
        }
      `}</style>
    </>
  );
}
