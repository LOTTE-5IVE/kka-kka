import { useState } from "react";
import ApplyGrade from "./ApplyGrade";
import ApplyProduct from "./ApplyProduct";
import axios from "axios";
import Button from "../common/Button/Button";
import ApplyCategory from "./ApplyCategory";
import { AdminButton } from "../common/Button/AdminButton";

export default function CouponSearchTable() {
  const [target, setTarget] = useState("카테고리");
  const [promotionName, setPromotionName] = useState("");
  const [discount, setDiscount] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [targetVal, setTargetVal] = useState("1");

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
      <table>
        <colgroup>
          <col style={{ width: "5%" }} />
          <col style={{ width: "8%" }} />
          <col style={{ width: "22%" }} />
          <col style={{ width: "15%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "16%" }} />
          <col style={{ width: "16%" }} />
          <col style={{ width: "8%" }} />
        </colgroup>
        <thead>
          <th></th>
          <th>혜택 유형</th>
          <th>혜택 이름</th>
          <th>할인 설정</th>
          <th>최소 주문 금액</th>
          <th>다운로드 가능 기간</th>
          <th>유효기간</th>
          <th>적용 유형</th>
        </thead>
        <tbody>
          <tr style={{ height: "5vw" }}>
            <td>
              <AdminButton context="중지" color="#F2B90C" />
            </td>
            <td>쿠폰</td>
            <td>스낵/쿠키 카테고리 쿠폰</td>
            <td>15% (최대 5,000원)</td>
            <td>10,000원</td>
            <td>2022-09-15 ~ 2022-09-15</td>
            <td>2022-09-15 ~ 2022-09-15</td>
            <td>회원 등급</td>
          </tr>
          <tr style={{ height: "5vw" }}>
            <td>
              <AdminButton context="중지" color="#F2B90C" />
            </td>
            <td>쿠폰</td>
            <td>스낵/쿠키 카테고리 쿠폰</td>
            <td>15% (최대 5,000원)</td>
            <td>10,000원</td>
            <td>2022-09-15 ~ 2022-09-15</td>
            <td>2022-09-15 ~ 2022-09-15</td>
            <td>상품</td>
          </tr>
          <tr style={{ height: "100%" }}>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>

      <style jsx>{`
        table {
          height: 100%;
          width: 100%;
          text-align: center;
          border-collapse: collapse;

          td {
            border: 1px solid #dedede;
          }

          th {
            border-left: 1px solid #dedede;
          }

          th:first-child {
            border-left: 0;
          }

          td:first-child {
            border-left: 0;
          }

          td:last-child {
            border-right: 0;
          }

          tr:last-child {
            td {
              border-bottom: 0;
            }
          }
        }
      `}</style>
    </>
  );
}
