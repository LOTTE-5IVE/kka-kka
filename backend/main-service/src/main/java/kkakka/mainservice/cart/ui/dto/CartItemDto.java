package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemDto {

    private Long cartItemId;
    private Long productId;
    private String productName;
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
                c.getProduct().getImageUrl(),
                null,
                c.getQuantity(),
                c.getProduct().getPrice(),
                c.getQuantity() * c.getProduct().getPrice());
    }

}
