package kkakka.mainservice.product.application.dto;

import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.ui.dto.ProductResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {

    private Long id;
    private CategoryDto categoryDto;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    private int discount;

    public static ProductDto toDto(Product product, CategoryDto categoryDto) {
        return new ProductDto(product.getId(),
                categoryDto,
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getDetailImageUrl(),
                product.getNutritionInfoUrl(),
                product.getDiscount()
        );
    }

    public ProductResponse toResponseDto() {
        return new ProductResponse(
                this.id,
                this.categoryDto.toResponseDto(),
                this.name,
                this.price,
                this.stock,
                this.imageUrl,
                this.detailImageUrl,
                this.nutritionInfoUrl,
                this.discount
        );
    }
}
