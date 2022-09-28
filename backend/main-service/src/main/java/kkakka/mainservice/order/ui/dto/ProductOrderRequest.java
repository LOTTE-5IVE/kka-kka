package kkakka.mainservice.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductOrderRequest {

    private Long productId;
    private Integer quantity;

    @Override
    public String toString() {
        return "productOrderRequest{" +
            "productId=" + productId +
            ", quantity=" + quantity +
            '}';
    }
}
