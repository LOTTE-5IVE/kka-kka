package kkakka.mainservice.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    @JsonProperty("category")
    private CategoryResponse categoryResponse;
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

    public static ProductResponseDto create(Product product) {
        return new ProductResponseDto(product.getId(),
            CategoryResponse.create(product.getCategory()),
            product.getName(),
            product.getPrice(),
            product.getStock(),
            product.getImageUrl(),
            product.getDetailImageUrl(),
            product.getNutritionInfoUrl(),
            product.getDiscount()
        );
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryResponse {

        private Long id;
        private String name;

        public static CategoryResponse create(Category category) {
            return new CategoryResponse(category.getId(),
                category.getName());
        }
    }


}
