package kkakka.mainservice.coupon.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
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
public class CouponProductResponseDto {

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

    static final BiFunction<Integer, Integer, Integer> calculateStaticPrice = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer productPrice, Integer couponDiscount) {
            return productPrice - couponDiscount;
        }
    };

    static final BiFunction<Integer, Integer, Integer> calculatePercentage = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer productPrice, Integer couponPercentage) {
            return (int) Math.ceil(productPrice * (1 - (couponPercentage * 0.01)));
        }
    };

    public static CouponProductResponseDto create(Coupon coupon, Boolean isDownloadable,
        Integer productPrice) {
        if (coupon.getPercentage() != null) {
            int calculatedPercentValue = calculatePercentage.apply(
                productPrice, coupon.getPercentage());
            int calculatedStaticValue = calculateStaticPrice.apply(productPrice,
                coupon.getMaxDiscount());
            return new CouponProductResponseDto(
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
                isDownloadable, Math.max(calculatedPercentValue, calculatedStaticValue));
        }
        return new CouponProductResponseDto(
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
            isDownloadable, calculateStaticPrice.apply(productPrice,
            coupon.getMaxDiscount()));
    }


}
