package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Coupon coupon;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    public static CartItemDto from(CartItem c) {
        return new CartItemDto(
                c.getId(),
                c.getProduct().getId(),
                c.getProduct().getName(),
                c.getProduct().getImageUrl(),
                null,
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getPrice());
    }

    public static CartItemDto applyCouponDto(CartItem c) {
        return new CartItemDto(
                c.getId(),
                c.getProduct().getId(),
                c.getProduct().getName(),
                c.getProduct().getImageUrl(),
                c.getCoupon(),
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getPrice());
    }

}
