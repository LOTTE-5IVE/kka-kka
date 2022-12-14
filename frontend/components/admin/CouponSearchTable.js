import { useContext, useEffect, useState } from "react";
import axios from "axios";
import { AdminButton } from "../common/Button/AdminButton";
import { commaMoney } from "../../hooks/commaMoney";
import { UserContext } from "../../context/AdminTokenContext";

export default function CouponSearchTable() {
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

  const [coupons, setCoupons] = useState();
  const userData = useContext(UserContext)?.adminUser;

  const getCoupon = async () => {
    await axios
      .get("/api/coupons", {
        headers: {
          Authorization: `Bearer ${userData.adminToken}`,
        },
      })
      .then((res) => {
        setCoupons(res.data);
      });
  };

  const deleteCoupon = async (id) => {
    await axios
      .put(`/api/coupons/${id}`, null, {
        headers: {
          Authorization: `Bearer ${userData.adminToken}`,
        },
      })
      .then((res) => {});

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
          <col style={{ width: "5%" }} />
          <col style={{ width: "15%" }} />
          <col style={{ width: "5%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "10%" }} />
          <col style={{ width: "15%" }} />
          <col style={{ width: "15%" }} />
          <col style={{ width: "15%" }} />
        </colgroup>
        <thead>
          <tr>
            <th></th>
            <th>혜택 유형</th>
            <th>혜택 이름</th>
            <th>할인 설정</th>
            <th>최소 주문 금액</th>
            <th>최대 할인 금액</th>
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
                <td>{coupon.percentage ? `${coupon.percentage}%` : "x"}</td>
                <td>{commaMoney(coupon.minOrderPrice) || 0}원</td>
                <td>{commaMoney(coupon.maxDiscount)}원</td>
                <td>
                  {coupon.startedAt.slice(0, 10)} ~{" "}
                  {coupon.expiredAt.slice(0, 10)}
                </td>
                <td>{coupon.registeredAt}</td>
                <td>
                  {coupon.categoryId
                    ? `카테고리(${cat_name[coupon.categoryId]})`
                    : coupon.grade
                    ? `등급(${coupon.grade})`
                    : `상품(ID: ${coupon.productId})`}
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
