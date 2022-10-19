import { useState } from "react";
import Button from "../common/Button/Button";
import ApplyGrade from "./ApplyGrade";
import ApplyCategory from "./ApplyCategory";
import ApplyProduct from "./ApplyProduct";
import { PostHApi } from "../../apis/Apis";
import axios from "axios";
import DiscountEnrollTable from "./DiscountEnrollTable";
import CouponEnrollTable from "./CouponEnrollTable";

export default function PromotionEnroll() {
  const [btn, setBtn] = useState("할인");
  const [valid, setValid] = useState("기간");
  const [target, setTarget] = useState("카테고리");
  const [promotionName, setPromotionName] = useState("");
  const [discount, setDiscount] = useState();
  const [maxdis, setMaxdis] = useState();
  const [minorder, setMinorder] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [targetVal, setTargetVal] = useState("1");

  const handleChange = (e) => {
    // <- input값으로 text 변경 함수
    this.setState({
      text: e.target.value,
    });
  };

  const makeDiscount = async () => {
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
          </tbody>
        </table>
        {btn === "할인" ? <DiscountEnrollTable /> : <CouponEnrollTable />}

        {/* <div className="btnWrapper">
          <div
            onClick={() => {
              console.log("click");
              makeDiscount();
            }}
          >
            <Button context="혜택 등록하기" color="#F2889B" tcolor="#fff" />
          </div>
          <Button context="취소" border="1px solid" />
        </div> */}
      </div>

      <style jsx>{`
        .contents {
          height: 105%;
          border: 2px solid #dedede;
          color: #7a7a7a;

          table {
            width: 90%;
            height: 10%;
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
