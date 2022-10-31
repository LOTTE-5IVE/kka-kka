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
    private LocalDateTime orderedAt;
    private Integer totalPrice;
    @JsonProperty("productOrders")
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