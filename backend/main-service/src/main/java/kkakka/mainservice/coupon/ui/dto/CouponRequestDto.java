package kkakka.mainservice.coupon.ui.dto;

import java.time.LocalDateTime;
import kkakka.mainservice.member.member.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponRequestDto {

    private Long id;
    private Long categoryId;
    private Grade grade;
    private Long productId;
    private String name;
    private String detail;
    private String priceRule;
    private LocalDateTime registeredAt;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    private Integer percentage;
    private Integer maxDiscount;
    private Integer minOrderPrice;
    private Integer isUsed;
}
