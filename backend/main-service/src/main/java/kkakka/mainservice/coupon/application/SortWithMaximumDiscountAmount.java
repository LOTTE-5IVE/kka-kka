package kkakka.mainservice.coupon.application;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import kkakka.mainservice.coupon.ui.dto.CouponProductResponseDto;
import kkakka.mainservice.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class SortWithMaximumDiscountAmount {

    BiFunction<Integer, Integer, Integer> calculateStaticPrice = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer productPrice, Integer couponDiscount) {
            return productPrice - couponDiscount;
        }
    };

    BiFunction<Integer, Integer, Integer> calculatePercentage = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer productPrice, Integer couponPercentage) {
            return (int) Math.ceil(productPrice * (1 - (couponPercentage * 0.01)));
        }
    };

    public List<CouponProductResponseDto> sort(
        List<CouponProductResponseDto> couponProductResponseDtos, Product product) {
        for (CouponProductResponseDto couponProductResponseDto : couponProductResponseDtos) {
            if (couponProductResponseDto.getPercentage() != null) {
                couponProductResponseDto.saveDiscountedPrice(
                    calculatePercentage.apply(product.getPrice() - product.getDiscount(),
                        couponProductResponseDto.getPercentage()));
                continue;
            }
            couponProductResponseDto.saveDiscountedPrice(
                calculateStaticPrice.apply(product.getPrice() - product.getDiscount(),
                    couponProductResponseDto.getMaxDiscount()));
        }
        return couponProductResponseDtos.stream()
            .sorted(Comparator.comparing(CouponProductResponseDto::getDiscountedPrice))
            .collect(Collectors.toList());
    }
}
