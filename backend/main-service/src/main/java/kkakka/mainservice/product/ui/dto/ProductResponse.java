package kkakka.mainservice.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    @JsonProperty("category")
    private ProductCategoryResponse categoryResponse;
    private String name;
    private Integer price;
    private Integer stock;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("detailImage_url")
    private String detailImageUrl;
    @JsonProperty("nutritionInfo_url")
    private String nutritionInfoUrl;
    private int discount;

    public static ProductResponse create(Product product) {
        return new ProductResponse(product.getId(),
                ProductCategoryResponse.create(product.getCategory()),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getDetailImageUrl(),
                product.getNutritionInfoUrl(),
                product.getDiscount()
        );
    }
}
