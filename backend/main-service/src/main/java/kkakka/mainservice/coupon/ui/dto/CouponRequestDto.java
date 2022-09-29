package kkakka.mainservice.coupon.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponRequestDto {

    private Long id;
    private Long categoryId;
    private String grade;
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
