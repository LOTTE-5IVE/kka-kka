import { useContext, useEffect, useState } from "react";
import axios from "axios";
import { AdminButton } from "../common/Button/AdminButton";
import { UserContext } from "../../context/AdminTokenContext";

export default function DiscountSearchTable() {
  const [discounts, setDiscounts] = useState();
  const userData = useContext(UserContext)?.adminUser;

  const cat_name = {
    0: "전체 상품",
    1: "비스킷/샌드",
    2: "스낵/봉지과자",
    3: "박스과자",
    4: "캔디/사탕/젤리",
    5: "시리얼/바",
    6: "초콜릿",
    7: "껌/자일리톨",
    8: "선물세트",
  };

  const getDiscount = async () => {
    await axios
      .get("/api/coupons/discount", {
        headers: {
          Authorization: `Bearer ${userData.adminToken}`,
        },
      })
      .then((res) => {
        setDiscounts(res.data);
      });
  };

  const deleteDiscount = async (id) => {
    await axios
      .put(
        `/api/coupons/discount/${id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${userData.adminToken}`,
          },
        },
      )
      .then((res) => {});

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
          <col style={{ width: "25%" }} />
          <col style={{ width: "5%" }} />
          <col style={{ width: "20%" }} />
          <col style={{ width: "15%" }} />
        </colgroup>
        <thead>
          <tr>
            <th></th>
            <th>혜택 유형</th>
            <th>혜택 이름</th>
            <th>할인 설정</th>
            <th>유효기간</th>
            <th>적용 유형</th>
          </tr>
        </thead>
        <tbody>
          {discounts?.map((discount) => {
            return (
              <tr style={{ height: "3vw" }} key={discount.id}>
                <td
                  onClick={() => {
                    deleteDiscount(discount.id);
                  }}
                >
                  <AdminButton context="중지" color="#F2B90C" />
                </td>
                <td>할인</td>
                <td>{discount.name}</td>
                <td>{discount.discount}%</td>
                <td>
                  {discount.startedAt.slice(0, 10)} ~{" "}
                  {discount.expiredAt.slice(0, 10)}
                </td>
                <td>
                  {discount.categoryId
                    ? `카테고리(${cat_name[discount.categoryId]})`
                    : `상품(ID: ${discount.productId})`}
                </td>
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
          overflow: auto;
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
