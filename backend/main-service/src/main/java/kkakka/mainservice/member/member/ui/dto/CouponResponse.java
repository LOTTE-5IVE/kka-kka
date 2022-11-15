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
    private PriceRule priceRule;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registeredAt;

    private Integer percentage;
    private Integer maxDiscount;

    public static CouponResponse create(
        Long id,
        String name,
        PriceRule priceRule,
        LocalDateTime registeredAt,
        Integer percentage,
        Integer maxDiscount
    ) {
        return new CouponResponse(id, name, priceRule, registeredAt, percentage, maxDiscount);
    }
}
