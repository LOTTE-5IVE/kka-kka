package kkakka.mainservice.product.application.dto;

import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.ui.dto.ProductDetailResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDetailDto {

    private Long id;
    private CategoryDto categoryDto;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    private int discount;
    private NutritionDto nutritionDto;

    public static ProductDetailDto toDto(Product product, CategoryDto categoryDto,
            NutritionDto nutritionDto) {
        return new ProductDetailDto(product.getId(),
                categoryDto,
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getDetailImageUrl(),
                product.getNutritionInfoUrl(),
                product.getDiscount(),
                nutritionDto
        );
    }

    public ProductDetailResponse toResponseDto() {
        return new ProductDetailResponse(
                this.id,
                this.categoryDto.toResponseDto(),
                this.name,
                this.price,
                this.stock,
                this.imageUrl,
                this.detailImageUrl,
                this.nutritionInfoUrl,
                this.discount,
                this.nutritionDto.toResponseDto()
        );
    }
}