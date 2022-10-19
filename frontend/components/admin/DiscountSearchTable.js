import { useEffect, useState } from "react";
import ApplyGrade from "./ApplyGrade";
import ApplyProduct from "./ApplyProduct";
import axios from "axios";
import Button from "../common/Button/Button";
import ApplyCategory from "./ApplyCategory";
import { AdminButton } from "../common/Button/AdminButton";

export default function DiscountSearchTable() {
  const [discounts, setDiscounts] = useState();

  const getDiscount = async () => {
    await axios.get("/api/coupons/discount").then((res) => {
      console.log(res.data);
      setDiscounts(res.data);
    });
  };

  const deleteDiscount = async (id) => {
    await axios.put(`/api/coupons/discount/${id}`).then((res) => {
      console.log(res);
    });

    getDiscount();
  };

  useEffect(() => {
    getDiscount();
  }, []);

  return (
    <>
      <table>
        <colgroup>
          <col style={{ width: "5%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "30%" }} />
          <col style={{ width: "5%" }} />
          <col style={{ width: "20%" }} />
          <col style={{ width: "10%" }} />
        </colgroup>
        <thead>
          <th></th>
          <th>혜택 유형</th>
          <th>혜택 이름</th>
          <th>할인 설정</th>
          <th>유효기간</th>
          <th>적용 유형</th>
        </thead>
        <tbody>
          {discounts?.map((discount) => {
            return (
              <tr style={{ height: "3vw" }}>
                <td
                  onClick={() => {
                    console.log("click");
                    deleteDiscount(discount.id);
                  }}
                >
                  <AdminButton context="중지" color="#F2B90C" />
                </td>
                <td>할인</td>
                <td>{discount.name}</td>
                <td>{discount.discount}%</td>
                <td>
                  {discount.startedAt} ~ {discount.expiredAt}
                </td>
                <td>{discount.categoryId ? "카테고리" : "상품"}</td>
              </tr>
            );
          })}
          <tr style={{ height: "100%" }}>
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
