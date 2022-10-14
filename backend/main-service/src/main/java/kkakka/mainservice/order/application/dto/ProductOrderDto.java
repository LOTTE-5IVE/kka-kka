package kkakka.mainservice.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private Long productId;
    private Integer quantity;
}
