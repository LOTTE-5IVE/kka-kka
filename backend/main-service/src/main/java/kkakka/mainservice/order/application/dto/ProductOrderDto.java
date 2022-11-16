package kkakka.mainservice.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private Long productId;
    private Long couponId;
    private Integer quantity;

    public Long getCouponId() {
        if (couponId == null) {
            return null;
        }
        return this.couponId;
    }
}
