package kkakka.mainservice.cart.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRequestDto {
    private Long memberId;
    private Long productId;
    private Integer quantity;
}
