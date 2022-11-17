package kkakka.mainservice.member.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderResponse {

    private Long id;
    private Integer price;
    private Integer discount;
    private Integer quantity;
    private Integer deleted;
    @JsonProperty("coupon")
    private CouponResponse couponResponse;
    @JsonProperty("product")
    private ProductResponse productResponse;
    @JsonProperty("review")
    private ReviewSimpleResponse reviewResponse;

    public static ProductOrderResponse create(
        Long id,
        Integer price,
        Integer discount,
        Integer quantity,
        Integer deleted,
        CouponResponse couponResponse,
        ProductResponse productResponse,
        ReviewSimpleResponse reviewResponse) {
        return new ProductOrderResponse(id, price, discount, quantity, deleted, couponResponse,
            productResponse, reviewResponse);
    }
}

