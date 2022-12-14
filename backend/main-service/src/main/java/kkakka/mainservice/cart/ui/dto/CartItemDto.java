package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemDto {

    private Long cartItemId;
    private Long id;  // product id
    private String name;  // product name
    private Integer discount;  // product discount
    private String imageUrl;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer totalDiscount;
    private CouponDto couponDto;

    public static CartItemDto createWithoutCoupon(CartItem c) {
        return new CartItemDto(
                c.getId(),
                c.getProduct().getId(),
                c.getProduct().getName(),
                c.getProduct().getDiscount(),
                c.getProduct().getImageUrl(),
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getPrice(),
                0,
                0,
                null);
    }

    public static CartItemDto createWithCoupon(CartItem c, Integer discountedPrice, Coupon coupon) {
        return new CartItemDto(
                c.getId(),
                c.getProduct().getId(),
                c.getProduct().getName(),
                c.getProduct().getDiscount(),
                c.getProduct().getImageUrl(),
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getPrice(),
                discountedPrice,
                c.getPrice() - discountedPrice,
                CouponDto.toDto(coupon));
    }

}
