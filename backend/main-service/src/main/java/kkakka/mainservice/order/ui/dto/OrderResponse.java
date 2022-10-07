package kkakka.mainservice.order.ui.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private LocalDateTime orderedAt;
    private Integer totalPrice;
    @JsonProperty("productOrders")
    private List<ProductOrderResponse> productOrderResponse;

    public static OrderResponse create(
        Long id,
        LocalDateTime orderedAt,
        Integer totalPrice,
        List<ProductOrderResponse> productOrderResponse
    ) {
        return new OrderResponse(id, orderedAt, totalPrice, productOrderResponse);
    }
}
