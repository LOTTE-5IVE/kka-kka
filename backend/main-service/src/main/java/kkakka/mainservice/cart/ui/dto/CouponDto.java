package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponDto {

    private Long id;
    private String name;
    private Integer percentage;
    private Integer maxDiscount;
    private Integer minOrderPrice;

    public static CouponDto toDto(Coupon coupon) {
        return new CouponDto(coupon.getId(),
                coupon.getName(),
                coupon.getPercentage(),
                coupon.getMaxDiscount(),
                coupon.getMinOrderPrice());
    }
}
