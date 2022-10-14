package kkakka.mainservice.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public Long getCategoryId() {
        return categoryDto.getId();
    }

    public String getCategoryName() {
        return categoryDto.getName();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDto {

        private Long id;
        private String name;
    }
}
