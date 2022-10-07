package kkakka.mainservice.cart.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private Long cartNo;
    private List<CartItemDto> cartItemList;
}
