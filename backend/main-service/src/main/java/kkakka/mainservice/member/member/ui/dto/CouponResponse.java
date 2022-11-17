package kkakka.mainservice.member.member.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import kkakka.mainservice.coupon.domain.PriceRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CouponResponse {

    private Long id;
    private String name;
    private Integer discountedPrice;

    public static CouponResponse create(
        Long id,
        String name,
        Integer discountedPrice
    ) {
        return new CouponResponse(id, name, discountedPrice);
    }
}
