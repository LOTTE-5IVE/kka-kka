package kkakka.mainservice.cart.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {

    private Long productId;
    private Integer quantity;
}
