import { useEffect, useState } from "react";
import { GetHApi } from "../../apis/Apis";
import { useGetToken } from "../../hooks/useGetToken";
import { useMoney } from "../../hooks/useMoney";

export default function MyCouponCard({ test }) {
  const [coupons, setCoupons] = useState();

  const getCoupon = async () => {
    GetHApi("/api/coupons/me", useGetToken()).then((res) => {
      if (res) {
        setCoupons(res);
      }
    });
  };

  useEffect(() => {
    getCoupon();
  }, []);

  return (
    <>
      {coupons?.map((coupon) => {
        return (
          <tr style={{ height: "3vw" }} key={coupon.id}>
            <td>{coupon.name} </td>
            <td>
              {coupon.percentage}% (최대 {useMoney(coupon.maxDiscount)}원){" "}
            </td>
            <td>{useMoney(coupon.minOrderPrice)}원 이상 구매시</td>
            <td>
              {coupon.startedAt.slice(0, 10)} ~ {coupon.expiredAt.slice(0, 10)}
            </td>
            <td>
              {coupon.categoryId
                ? "카테고리"
                : coupon.productId
                ? "상품"
                : "등급"}
            </td>
          </tr>
        );
      })}
    </>
  );
}
