package kkakka.mainservice.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderMessage {

    private Long id;
    private Integer price;
    private Integer quantity;
    private Integer deleted;
    @JsonProperty("product")
    private ProductMessage productMessage;

    public static ProductOrderMessage create(Long id, Integer price,
            Integer quantity, Integer deleted, ProductMessage productMessage
    ) {
        return new ProductOrderMessage(id, price, quantity, deleted, productMessage);
    }
}