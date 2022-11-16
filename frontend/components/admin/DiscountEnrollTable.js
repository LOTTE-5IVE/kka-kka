import { useContext, useState, useEffect } from "react";
import ApplyProduct from "./ApplyProduct";
import axios from "axios";
import Button from "../common/Button/Button";
import ApplyCategory from "./ApplyCategory";
import { UserContext } from "../../context/AdminTokenContext";
import {isNumber} from "../../hooks/isNumber";

export default function DiscountEnrollTable() {
  const [target, setTarget] = useState("카테고리");
  const [promotionName, setPromotionName] = useState("");
  const [discount, setDiscount] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [targetVal, setTargetVal] = useState(1);
  const [productId, setProductId] = useState(1);
  const [nameValid, setNameValid] = useState(false);

  const [discountValid, setDiscountValid] = useState(false);
  const [sDateValid, setSDateValid] = useState(false);
  const [eDateValid, setEDateValid] = useState(false);
  const [unvalid, setUnValid] = useState(true);
  const today = new Date().toISOString().substring(0, 10);

  const userData = useContext(UserContext)?.adminUser;

  const makeDiscount = async () => {
    await axios
      .post("/api/coupons/discount", {
        categoryId: targetVal,
        productId: null,
        name: promotionName,
        discount: discount,
        discountType: "CATEGORY_DISCOUNT",
        startedAt: `${startDate} 00:00:00`,
        expiredAt: `${endDate} 00:00:00`,
      }, {
        headers: {
          Authorization: `Bearer ${userData.adminToken}`
        }
      })
      .then((res) => {
        alert("등록완료!");
      })
      .catch((err) => {
        alert("등록실패!");
      });
  };

  const makeDiscountProduct = async () => {
    await axios
      .post("/api/coupons/discount", {
        categoryId: null,
        productId: productId,
        name: promotionName,
        discount: discount,
        discountType: "PRODUCT_DISCOUNT",
        startedAt: `${startDate} 00:00:00`,
        expiredAt: `${endDate} 00:00:00`,
      }, {
        headers: {
          Authorization: `Bearer ${userData.adminToken}`
        }
      })
      .then((res) => {
        alert("등록완료!");
      })
      .catch((err) => {
        alert("등록실패!");
      });
  };

  useEffect(() => {
    if (nameValid && discountValid & sDateValid & eDateValid) {
      setUnValid(false);
    } else {
      setUnValid(true);
    }
  }, [nameValid, discountValid, sDateValid, eDateValid]);

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
                    defaultValue={discount}
                    onChange={(e) => {
                      if (isNumber(e.target.value)) {
                        setDiscount(e.target.value);
                        if (e.target.value.length > 0) setDiscountValid(true);
                        else {
                          setDiscountValid(false);
                        }
                      } else {
                        alert("숫자만 입력할 수 있습니다.");
                        setDiscountValid(false);
                        e.target.value = "";
                      }
                    }}
                  />{" "}
                  %
                </div>
              </td>
            </tr>

            <tr style={{ height: "5vw" }}>
              <th scope="row">유효 기간</th>

              <td>
                <div className="dateWrapper" style={{ display: "flex" }}>
                  <div className="date">
                    <input
                      id="oname"
                      className="inputTypeText"
                      type="date"
                      defaultValue={startDate}
                      onChange={(e) => {
                        if (e.target.value >= endDate) {
                          alert("날짜를 다시 설정해주세요.");
                          e.target.value = "";
                          setStartDate("");
                          setSDateValid(false);
                        } else {
                          setStartDate(e.target.value + "");
                          setSDateValid(true);
                        }
                      }}
                    />
                    {"  "}~{"  "}
                    <input
                      id="oname"
                      className="inputTypeText"
                      type="date"
                      defaultValue={endDate}
                      onChange={(e) => {
                        if (
                          e.target.value <= today ||
                          e.target.value <= startDate
                        ) {
                          alert("날짜를 다시 설정해주세요.");
                          e.target.value = "";
                          setEndDate("");
                          setEDateValid(false);
                        } else {
                          setEndDate(e.target.value + "");
                          setEDateValid(true);
                        }
                      }}
                    />
                  </div>
                </div>
              </td>
            </tr>
            <tr style={{ height: "20vw" }}>
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
                </div>
                {target == "카테고리" ? (
                  <ApplyCategory
                    targetVal={targetVal}
                    setTargetVal={setTargetVal}
                  />
                ) : (
                  <ApplyProduct
                    productId={productId}
                    setProductId={setProductId}
                  />
                )}
              </td>
            </tr>
          </tbody>
        </table>

        <div className="btnWrapper">
          <div
            onClick={() => {
              if (target === "카테고리") {
                makeDiscount();
              } else {
                makeDiscountProduct();
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
            height: 90%;
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
