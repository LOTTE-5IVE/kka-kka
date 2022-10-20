package kkakka.mainservice.coupon.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import kkakka.mainservice.coupon.domain.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDto {

    private Long id;
    private Long categoryId;
    private Long productId;
    private String name;
    private Integer discount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startedAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expiredAt;

    public static DiscountResponseDto create(Discount discount) {
        return new DiscountResponseDto(
            discount.getId(),
            discount.getCategoryId(),
            discount.getProductId(),
            discount.getName(),
            discount.getDiscount(),
            discount.getStartedAt(),
            discount.getExpiredAt());
    }
}
