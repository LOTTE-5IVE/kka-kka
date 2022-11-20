import { useEffect, useState } from "react";
import { GetHApi } from "../../apis/Apis";
import { getToken } from "../../hooks/getToken";
import { commaMoney } from "../../hooks/commaMoney";

export default function MyCouponCard({ coupons }) {
  // const [coupons, setCoupons] = useState();

  // const getCoupon = async () => {
  //   GetHApi("/api/coupons/me", getToken()).then((res) => {
  //     if (res) {
  //       setCoupons(res);
  //     }
  //   });
  // };

  // useEffect(() => {
  //   getCoupon();
  // }, []);

  return (
    <>
      {coupons?.map((coupon) => {
        return (
          <tr style={{ height: "3vw" }} key={coupon.id}>
            <td>{coupon.name} </td>
            <td>
              {coupon.percentage}% (최대 {commaMoney(coupon.maxDiscount)}원){" "}
            </td>
            <td>{commaMoney(coupon.minOrderPrice) || 0}원 이상 구매시</td>
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
