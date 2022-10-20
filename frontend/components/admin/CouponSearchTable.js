import { useEffect, useState } from "react";
import axios from "axios";
import { AdminButton } from "../common/Button/AdminButton";
import { useMoney } from "../../hooks/useMoney";

export default function CouponSearchTable() {
  const [coupons, setCoupons] = useState();

  const getCoupon = async () => {
    await axios.get("/api/coupons").then((res) => {
      setCoupons(res.data);
    });
  };

  const deleteCoupon = async (id) => {
    await axios.put(`/api/coupons/${id}`).then((res) => {});

    getCoupon();
  };

  useEffect(() => {
    getCoupon();
  }, []);

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
          <tr>
            <th></th>
            <th>혜택 유형</th>
            <th>혜택 이름</th>
            <th>할인 설정</th>
            <th>최소 주문 금액</th>
            <th>유효기간</th>
            <th>등록일</th>
            <th>적용 유형</th>
          </tr>
        </thead>
        <tbody>
          {coupons?.map((coupon) => {
            return (
              <tr style={{ height: "50px" }} key={coupon.id}>
                <td
                  onClick={() => {
                    deleteCoupon(coupon.id);
                  }}
                >
                  <AdminButton context="중지" color="#F2B90C" />
                </td>
                <td>쿠폰</td>
                <td>{coupon.name}</td>
                <td>
                  {coupon.percentage}% (최대 {useMoney(coupon.maxDiscount)}원)
                </td>
                <td>{useMoney(coupon.minOrderPrice)}원</td>
                <td>
                  {coupon.startedAt.slice(0, 10)} ~{" "}
                  {coupon.expiredAt.slice(0, 10)}
                </td>
                <td>{coupon.registeredAt}</td>
                <td>
                  {coupon.categoryId
                    ? "카테고리"
                    : coupon.grade
                    ? "등급"
                    : "상품"}
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
