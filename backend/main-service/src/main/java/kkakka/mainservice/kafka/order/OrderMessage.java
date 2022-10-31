package kkakka.mainservice.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderMessage {

    private Long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("ordered_at")
    private LocalDateTime orderedAt;
    @JsonProperty("total_price")
    private Integer totalPrice;
    @JsonProperty("product_orders")
    private List<ProductOrderMessage> productOrderMessages;

    public static OrderMessage create(
            Long orderId,
            LocalDateTime orderedAt,
            Integer totalPrice,
            List<ProductOrderMessage> productOrderMessages
    ) {
        return new OrderMessage(orderId, orderedAt, totalPrice, productOrderMessages);
    }
}