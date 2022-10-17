package kkakka.mainservice.coupon.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiscountRequestDto {

    private Long categoryId;
    private Long productId;
    private String name;
    private Integer discount;
    private String discountType;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expiredAt;

    public boolean isValidateDate() {
        return this.getStartedAt().isBefore(LocalDateTime.now())
            && this.getExpiredAt().isAfter(LocalDateTime.now());
    }

    public boolean isValidateDiscount() {
        return this.discount < 100 && this.discount > 0;
    }
}
