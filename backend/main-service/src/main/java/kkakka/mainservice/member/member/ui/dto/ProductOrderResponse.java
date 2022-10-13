package kkakka.mainservice.member.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderResponse {

    private Long id;
    private Integer price;
    private Integer quantity;
    @JsonProperty("product")
    private ProductResponse productResponse;
    @JsonProperty("review")
    private ReviewSimpleRespnse reviewResponse;

    public static ProductOrderResponse create(Long id, Integer price,
            Integer quantity, ProductResponse productResponse, ReviewSimpleRespnse reviewResponse) {
        return new ProductOrderResponse(id, price, quantity, productResponse, reviewResponse);
    }
}

