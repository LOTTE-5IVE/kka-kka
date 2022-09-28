package kkakka.mainservice.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductOrderRequest {

    private Long productId;
    private Integer quantity;

    //==테스트용 생성자==//
    public ProductOrderRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductOrderRequest{" +
            "productId=" + productId +
            ", quantity=" + quantity +
            '}';
    }
}
