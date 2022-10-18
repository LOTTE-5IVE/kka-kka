package kkakka.mainservice.cart.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private Long cartId;
    @JsonProperty("cartItems")
    private List<CartItemDto> cartItemDtos;
}
