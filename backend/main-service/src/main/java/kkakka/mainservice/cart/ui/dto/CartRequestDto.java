package kkakka.mainservice.cart.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartRequestDto {

    private Long memberId;
    private Long productId;
    private Integer quantity;

    @Override
    public String toString() {
        return "CartRequestDto{" +
                "memberId=" + memberId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
