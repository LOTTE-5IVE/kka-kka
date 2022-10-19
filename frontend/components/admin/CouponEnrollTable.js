import { useState } from "react";
import ApplyGrade from "./ApplyGrade";
import ApplyProduct from "./ApplyProduct";
import axios from "axios";
import Button from "../common/Button/Button";

export default function CouponEnrollTable() {
  const [valid, setValid] = useState("기간");
  const [target, setTarget] = useState("카테고리");
  const [promotionName, setPromotionName] = useState("");
  const [discount, setDiscount] = useState();
  const [maxdis, setMaxdis] = useState();
  const [minorder, setMinorder] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [targetVal, setTargetVal] = useState("1");

  const makeCoupon = async () => {
    console.log(typeof startDate);
    console.log(startDate);
    await axios.post("/api/coupons/discount", {
      categoryId: targetVal,
      productId: null,
      name: promotionName,
      discount: discount,
      discountType: "CATEGORY_DISCOUNT",
      startedAt: `${startDate} 00:00:00`,
      expiredAt: `${endDate} 00:00:00`,
    });
  };

  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "20%" }} />
            <col style={{ width: "80%" }} />
          </colgroup>

          <tbody>
            <tr style={{ height: "4vw" }}>
              <th scope="row">혜택 이름</th>
              <td
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  height: "100%",
                }}
              >
                <div>
                  <input
                    id="oname"
                    className="inputTypeText"
                    size="25"
                    type="text"
                    defaultValue={promotionName}
                    onChange={(e) => {
                      setPromotionName(e.target.value);
                    }}
                  />
                </div>

                {/* <div style={{ width: "40%" }}>
                  <span>
                    수량 x{" "}
                    <input
                      id="oname"
                      className="inputTypeText"
                      size="1"
                      type="text"
                    />
                  </span>
                </div> */}
              </td>
            </tr>
            <tr style={{ height: "4vw" }}>
              <th scope="row">할인 설정</th>
              <td
                style={{
                  display: "flex",
                  alignItems: "center",
                  height: "100%",
                }}
              >
                <div style={{ width: "15%" }}>
                  <input
                    id="oname"
                    className="inputTypeText"
                    size="2"
                    type="text"
                    defaultValue={discount}
                    onChange={(e) => {
                      setDiscount(e.target.value);
                    }}
                  />
                  %
                </div>

                <div style={{ width: "25%" }}>
                  최대{" "}
                  <input
                    id="oname"
                    className="inputTypeText"
                    size="10"
                    type="text"
                    defaultValue={maxdis}
                    onChange={(e) => {
                      setMaxdis(e.target.value);
                    }}
                  />{" "}
                  원
                </div>
                <span style={{ color: "red" }}>
                  *최대 금액만 설정시 정액 할인
                </span>
              </td>
            </tr>

            <tr style={{ height: "3vw" }}>
              <th scope="row">최소 주문 금액</th>
              <td>
                <input
                  id="oname"
                  className="inputTypeText"
                  placeholder=""
                  size="10"
                  type="text"
                  defaultValue={minorder}
                  onChange={(e) => {
                    setMinorder(e.target.value);
                  }}
                />
                원
              </td>
            </tr>

            <tr style={{ height: "6vw" }}>
              <th scope="row">유효 기간</th>
              <td>
                <div style={{ display: "flex", marginBottom: "15px" }}>
                  <div
                    className={`btn ${valid === "기간" ? "active" : ""}`}
                    onClick={() => setValid("기간")}
                  >
                    기간으로 설정
                  </div>
                  <div
                    className={`btn ${valid === "발급일" ? "active" : ""}`}
                    onClick={() => setValid("발급일")}
                  >
                    발급일로부터 설정
                  </div>
                </div>
                <div className="dateWrapper" style={{ display: "flex" }}>
                  <div className="date">
                    <input
                      id="oname"
                      className="inputTypeText"
                      type="date"
                      defaultValue={startDate}
                      onChange={(e) => {
                        setStartDate(e.target.value + "");
                      }}
                    />{" "}
                    {startDate}~{" "}
                    <input
                      id="oname"
                      className="inputTypeText"
                      type="date"
                      defaultValue={endDate}
                      onChange={(e) => {
                        setEndDate(e.target.value + "");
                      }}
                    />
                  </div>
                  {valid == "발급일" ? (
                    <div style={{ marginLeft: "30px" }}>
                      발급일로부터{" "}
                      <input
                        id="oname"
                        className="inputTypeText"
                        size="1"
                        type="text"
                      />{" "}
                      일 유효
                    </div>
                  ) : (
                    ""
                  )}
                </div>
              </td>
            </tr>
            <tr style={{ height: "12vw" }}>
              <th scope="row">적용 대상 지정</th>
              <td>
                <div style={{ display: "flex", marginBottom: "15px" }}>
                  <div
                    className={`btn ${target === "카테고리" ? "active" : ""}`}
                    onClick={() => setTarget("카테고리")}
                  >
                    카테고리
                  </div>
                  <div
                    className={`btn ${target === "상품" ? "active" : ""}`}
                    onClick={() => setTarget("상품")}
                  >
                    상품
                  </div>
                  <div
                    className={`btn ${target === "회원 등급" ? "active" : ""}`}
                    onClick={() => setTarget("회원 등급")}
                  >
                    회원 등급
                  </div>
                </div>
                {target == "카테고리" ? (
                  <div className="outter">
                    <div>
                      <ul>
                        <li>
                          <input
                            type="radio"
                            value="1"
                            checked={targetVal === "1"}
                            onChange={(e) => {
                              setTargetVal(e.target.value);
                            }}
                          />
                          카테고리1
                        </li>
                        <li>
                          <input
                            type="radio"
                            value="2"
                            checked={targetVal === "2"}
                            onChange={(e) => {
                              setTargetVal(e.target.value);
                            }}
                          />
                          카테고리2
                        </li>
                        <li>
                          <input
                            type="radio"
                            value="3"
                            checked={targetVal === "3"}
                            onChange={(e) => {
                              setTargetVal(e.target.value);
                            }}
                          />
                          카테고리3
                        </li>
                        <li>{targetVal}</li>
                      </ul>
                    </div>
                  </div>
                ) : // <ApplyCategory />
                target == "상품" ? (
                  <ApplyProduct />
                ) : (
                  <ApplyGrade />
                )}
              </td>
            </tr>
          </tbody>
        </table>

        <div className="btnWrapper">
          <div
            onClick={() => {
              console.log("click");
              makeCoupon();
            }}
          >
            <Button context="혜택 등록하기" color="#F2889B" tcolor="#fff" />
          </div>
          <Button context="취소" border="1px solid" />
        </div>
      </div>

      <style jsx>{`
        .contents {
          height: 80%;
          color: #7a7a7a;

          table {
            width: 90%;
            height: 100%;
            margin: auto;
            border-collapse: collapse;
            th {
              text-align: left;
            }

            tr {
              border-bottom: 1px solid #dedede;
            }

            .btn {
              border: 1px solid;
              padding: 7px 25px;
            }

            .active {
              background-color: #f2889b;
              color: #fff;
              border: 1px solid #f2889b;
            }
          }

          .btnWrapper {
            display: flex;
            justify-content: center;
            margin: 20px 0;
            width: 33%;
            margin: 20px auto;
            justify-content: space-around;
          }
        }
      `}</style>
    </>
  );
}
