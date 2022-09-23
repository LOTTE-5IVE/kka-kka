package kkakka.mainservice.product.ui.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;
    private String categoryName;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    private int disCount;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
