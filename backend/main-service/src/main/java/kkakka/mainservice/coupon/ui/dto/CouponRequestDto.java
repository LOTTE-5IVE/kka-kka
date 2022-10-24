package kkakka.mainservice.coupon.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeDeserializer;
import kkakka.mainservice.member.member.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponRequestDto {

    private Long categoryId;
    private Grade grade;
    private Long productId;
    private String name;
    private String priceRule;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expiredAt;

    private Integer percentage;
    private Integer maxDiscount;
    private Integer minOrderPrice;

    public boolean isValidDate() {
        return (this.getStartedAt().isAfter(LocalDateTime.now())
            || this.getExpiredAt().isAfter(LocalDateTime.now()))
            && this.getStartedAt().isBefore(this.getExpiredAt());
    }

    public boolean isValidPercentage() {
        return this.percentage < 100 && this.percentage > 0;
    }
}
