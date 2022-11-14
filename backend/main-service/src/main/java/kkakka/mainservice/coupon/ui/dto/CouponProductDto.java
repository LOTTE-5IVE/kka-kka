package kkakka.mainservice.coupon.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.member.member.domain.Grade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CouponProductDto {

    private Long id;
    private Long categoryId;
    private Grade grade;
    private Long productId;
    private String name;
    private PriceRule priceRule;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registeredAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expiredAt;
    private Integer percentage;
    private Integer maxDiscount;
    private Integer minOrderPrice;
    private Boolean isDownloadable;
    private Integer discountedPrice;

    public static CouponProductDto create(Coupon coupon, Boolean isDownloadable) {
        return new CouponProductDto(
            coupon.getId(),
            coupon.getCategoryId(),
            coupon.getGrade(),
            coupon.getProductId(),
            coupon.getName(),
            coupon.getPriceRule(),
            coupon.getRegisteredAt(),
            coupon.getStartedAt(),
            coupon.getExpiredAt(),
            coupon.getPercentage(),
            coupon.getMaxDiscount(),
            coupon.getMinOrderPrice(),
            isDownloadable, 0);
    }

    public void saveDiscountedPrice(Integer discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void downloadCoupon() {
        this.isDownloadable = false;
    }
}
