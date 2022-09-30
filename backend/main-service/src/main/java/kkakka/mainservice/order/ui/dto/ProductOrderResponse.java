package kkakka.mainservice.order.ui.dto;

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
  private Integer count;
  @JsonProperty("product")
  private ProductResponse productResponse;

  public static ProductOrderResponse create(Long id, Integer price,
      Integer count, ProductResponse productResponse) {
    return new ProductOrderResponse(id, price, count, productResponse);
  }
}
