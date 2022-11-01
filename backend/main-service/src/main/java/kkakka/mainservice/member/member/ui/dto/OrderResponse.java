package kkakka.mainservice.member.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import kkakka.mainservice.order.ui.dto.RecipientResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderedAt;
    private Integer totalPrice;
    @JsonProperty("productOrders")
    private List<ProductOrderResponse> productOrderResponse;
    @JsonProperty("recipient")
    private RecipientResponse recipientResponse;

    public static OrderResponse create(
            Long orderId,
            LocalDateTime orderedAt,
            Integer totalPrice,
            List<ProductOrderResponse> productOrderResponse,
            RecipientResponse recipientResponse
    ) {
        return new OrderResponse(orderId, orderedAt, totalPrice, productOrderResponse, recipientResponse);
    }
}
