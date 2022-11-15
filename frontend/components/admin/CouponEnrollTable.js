import { useEffect, useRef, useState } from "react";
import ApplyGrade from "./ApplyGrade";
import ApplyProduct from "./ApplyProduct";
import axios from "axios";
import Button from "../common/Button/Button";
import ApplyCategory from "./ApplyCategory";
import ValidDuration from "./ValidDuration";
import ValidDate from "./ValidDate";
import { isNumber } from "../../hooks/isNumber";

export default function CouponEnrollTable() {
  const [valid, setValid] = useState("기간");
  const [target, setTarget] = useState("카테고리");
  const [promotionName, setPromotionName] = useState("");
  const [discount, setDiscount] = useState();
  const [maxdis, setMaxdis] = useState();
  const [minorder, setMinorder] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [targetVal, setTargetVal] = useState(1);
  const [productId, setProductId] = useState(1);
  const [grade, setGrade] = useState("BRONZE");

  const [nameValid, setNameValid] = useState(false);
  const [maxdisValid, setMaxdisValid] = useState(false);
  const [minorderValid, setMinorderValid] = useState(false);
  const [sDateValid, setSDateValid] = useState(false);
  const [eDateValid, setEDateValid] = useState(false);
  const [unvalid, setUnValid] = useState(true);

  const initStates = () => {
    setPromotionName("");
    setDiscount("");
    setMaxdis("");
    setMinorder("");
    setNameValid(false);
    setDiscountValid(false);
    setMaxdisValid(false);
    setMinorderValid(false);
    setUnValid(true);
  };

  const makeCoupon = async () => {
    await axios
      .post("/api/coupons", {
        categoryId: targetVal,
        grade: null,
        productId: null,
        name: promotionName,

        priceRule: "COUPON",
        startedAt: `${startDate} 00:00:00`,
        expiredAt: `${endDate} 00:00:00`,
        percentage: discount,
        maxDiscount: maxdis,
        minOrderPrice: minorder,
      })
      .then((res) => {
        alert("등록완료!");
        initStates();
      })
      .catch((err) => {
        alert("등록실패!");
      });
  };

  const makeCouponProduct = async () => {
    await axios
      .post("/api/coupons", {
        categoryId: null,
        grade: null,
        productId: productId,
        name: promotionName,
        priceRule: "COUPON",
        startedAt: `${startDate} 00:00:00`,
        expiredAt: `${endDate} 00:00:00`,
        percentage: discount,
        maxDiscount: maxdis,
        minOrderPrice: minorder,
      })
      .then((res) => {
        alert("등록완료!");
        initStates();
      })
      .catch((err) => {
        alert("등록실패!");
      });
  };

  const makeCouponGrade = async () => {
    await axios
      .post("/api/coupons", {
        categoryId: null,
        grade: grade,
        productId: null,
        name: promotionName,
        priceRule: "GRADE_COUPON",
        startedAt: `${startDate} 00:00:00`,
        expiredAt: `${endDate} 00:00:00`,
        percentage: discount,
        maxDiscount: maxdis,
        minOrderPrice: minorder,
      })
      .then((res) => {
        alert("등록완료!");
        initStates();
      })
      .catch((err) => {
        alert("등록실패!");
      });
  };

  useEffect(() => {
    setStartDate();
    setEndDate();
    setSDateValid(false);
    setEDateValid(false);
  }, [valid]);

  useEffect(() => {
    if (nameValid & maxdisValid & minorderValid & sDateValid & eDateValid) {
      setUnValid(false);
    } else {
      setUnValid(true);
    }
  }, [nameValid, maxdisValid, minorderValid, sDateValid, eDateValid]);

  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "20%" }} />
            <col style={{ width: "80%" }} />
          </colgroup>

          <tbody>
            <tr style={{ height: "3vw" }}>
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
                    value={promotionName}
                    onChange={(e) => {
                      if (e.target.value.length > 0) {
                        setPromotionName(e.target.value);
                        setNameValid(true);
                      } else {
                        setPromotionName(e.target.value);
                        setNameValid(false);
                      }
                    }}
                  />
                </div>
              </td>
            </tr>
            <tr style={{ height: "3vw" }}>
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
                    value={discount}
                    onChange={(e) => {
                      if (isNumber(e.target.value)) {
                        setDiscount(e.target.value);
                      } else {
                        alert("숫자만 입력할 수 있습니다.");
                        e.target.value = "";
                      }
                    }}
                  />{" "}
                  %
                </div>

                <div style={{ width: "25%" }}>
                  최대{" "}
                  <input
                    id="oname"
                    className="inputTypeText"
                    size="10"
                    type="text"
                    value={maxdis}
                    onChange={(e) => {
                      if (isNumber(e.target.value)) {
                        setMaxdis(e.target.value);
                        if (e.target.value.length > 0) setMaxdisValid(true);
                        else {
                          setMaxdisValid(false);
                        }
                      } else {
                        alert("숫자만 입력할 수 있습니다.");
                        setMaxdisValid(false);
                        e.target.value = "";
                      }
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
                  value={minorder}
                  onChange={(e) => {
                    if (isNumber(e.target.value)) {
                      setMinorder(e.target.value);
                      if (e.target.value.length > 0) setMinorderValid(true);
                      else {
                        setMinorderValid(false);
                      }
                    } else {
                      alert("숫자만 입력할 수 있습니다.");
                      setMinorderValid(false);
                      e.target.value = "";
                    }
                  }}
                />
                원
              </td>
            </tr>

            <tr style={{ height: "5vw" }}>
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
                {valid == "기간" ? (
                  <ValidDuration
                    setStartDate={setStartDate}
                    setEndDate={setEndDate}
                    setSDateValid={setSDateValid}
                    setEDateValid={setEDateValid}
                  />
                ) : (
                  <ValidDate
                    setStartDate={setStartDate}
                    setEndDate={setEndDate}
                    setSDateValid={setSDateValid}
                    setEDateValid={setEDateValid}
                  />
                )}
              </td>
            </tr>
            <tr style={{ height: "15vw", position: "relative" }}>
              <th scope="row">적용 대상 지정</th>
              <td style={{ position: "absolute", top: "5%" }}>
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
                  <ApplyCategory
                    targetVal={targetVal}
                    setTargetVal={setTargetVal}
                  />
                ) : target == "상품" ? (
                  <ApplyProduct
                    productId={productId}
                    setProductId={setProductId}
                  />
                ) : (
                  <ApplyGrade grade={grade} setGrade={setGrade} />
                )}
              </td>
            </tr>
          </tbody>
        </table>

        <div className="btnWrapper">
          <div
            onClick={() => {
              if (target === "카테고리") {
                makeCoupon();
              } else if (target === "상품") {
                makeCouponProduct();
              } else {
                makeCouponGrade();
              }
            }}
          >
            <Button
              context="혜택 등록하기"
              color="#fe5c57"
              tcolor="#fff"
              unvalid={unvalid}
            />
          </div>
        </div>
      </div>

      <style jsx>{`
        .contents {
          height: 80%;
          color: #2c2c2c;

          table {
            width: 90%;
            height: 102%;
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
              border-radius: 1em;
              margin-right: 15px;
              cursor: pointer;
            }

            .active {
              background-color: #fe5c57;
              color: #fff;
              border: 1px solid #fe5c57;
              border-radius: 1em;
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
