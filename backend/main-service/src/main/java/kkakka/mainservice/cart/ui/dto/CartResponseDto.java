package kkakka.mainservice.cart.ui.dto;

import kkakka.mainservice.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private Long cartId;
    private Long cartItemId;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Integer coupon_id;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    public static CartResponseDto from(CartItem c) {
        return new CartResponseDto(c.getCart().getId(),
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
