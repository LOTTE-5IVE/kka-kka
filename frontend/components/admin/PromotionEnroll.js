import { useState } from "react";
import Button from "../common/Button/Button";
import ApplyGrade from "./ApplyGrade";
import ApplyCategory from "./ApplyCategory";
import ApplyProduct from "./ApplyProduct";

export default function PromotionEnroll() {
  const [btn, setBtn] = useState("할인");
  const [valid, setValid] = useState("기간");
  const [target, setTarget] = useState("카테고리");

  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "20%" }} />
            <col style={{ width: "80%" }} />
          </colgroup>

          <tbody>
            <tr style={{ height: "6vw" }}>
              <th scope="row">혜택 유형</th>
              <td>
                <div style={{ display: "flex" }}>
                  <div
                    className={`btn ${btn === "할인" ? "active" : ""}`}
                    onClick={() => setBtn("할인")}
                  >
                    할인
                  </div>
                  <div
                    className={`btn ${btn === "쿠폰" ? "active" : ""}`}
                    onClick={() => setBtn("쿠폰")}
                  >
                    쿠폰
                  </div>
                </div>
              </td>
            </tr>
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
                  />
                </div>

                <div style={{ width: "40%" }}>
                  <span>
                    수량 x{" "}
                    <input
                      id="oname"
                      className="inputTypeText"
                      size="1"
                      type="text"
                    />
                  </span>
                </div>
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
                  defaultValue=""
                  type="text"
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
                    <input id="oname" className="inputTypeText" type="date" /> ~{" "}
                    <input id="oname" className="inputTypeText" type="date" />
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
                  <ApplyCategory />
                ) : target == "상품" ? (
                  <ApplyProduct />
                ) : (
                  <ApplyGrade />
                )}
              </td>
            </tr>
          </tbody>
        </table>
        <div className="btnWrapper">
          <Button context="혜택 등록하기" color="#F2889B" tcolor="#fff" />
          <Button context="취소" border="1px solid" />
        </div>
      </div>

      <style jsx>{`
        .contents {
          height: 105%;
          border: 2px solid #dedede;
          color: #7a7a7a;

          table {
            width: 90%;
            height: 85%;
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
