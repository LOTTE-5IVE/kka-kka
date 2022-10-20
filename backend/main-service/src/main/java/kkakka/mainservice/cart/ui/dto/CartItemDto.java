package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.cart.domain.CartItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private Integer productDiscount;
    private String imageUrl;
    private Integer coupon_id;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    public static CartItemDto from(CartItem c) {
        return new CartItemDto(
                c.getId(),
                c.getProduct().getId(),
                c.getProduct().getName(),
                c.getProduct().getDiscount(),
                c.getProduct().getImageUrl(),
                null,
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getPrice());
    }

}
