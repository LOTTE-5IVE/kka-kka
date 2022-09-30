package kkakka.mainservice.order.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

  private Long id;
  private String name;
  private Integer price;
  private String imageUrl;
  private Integer discount;

  public static ProductResponse create(Long id, String name, Integer price, String imageUrl,
      Integer discount) {
    return new ProductResponse(id, name, price, imageUrl, discount);
  }
}
