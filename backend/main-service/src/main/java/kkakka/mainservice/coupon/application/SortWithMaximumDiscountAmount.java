package kkakka.mainservice.coupon.application;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.coupon.ui.dto.CouponProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class SortWithMaximumDiscountAmount {

    public List<CouponProductResponseDto> sort(
        List<CouponProductResponseDto> couponProductResponseDtos) {
        return couponProductResponseDtos.stream()
            .sorted(Comparator.comparing(CouponProductResponseDto::getDiscountedPrice))
            .collect(Collectors.toList());
    }
}
