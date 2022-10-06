package kkakka.mainservice.product.ui.dto;

import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProductResponseDto {

    private Long id;
    private String categoryName;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    private int discount;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static ProductResponseDto create(Product product) {
        return new ProductResponseDto(product.getId(),
            product.getCategory().getName(),
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
