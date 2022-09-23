package kkakka.mainservice.product.ui.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;
    private String categoryName;
    private String name;
    private Integer price;
    private Integer stock;
    private String image_url;
    private String detail_image_url;
    private String nutrition_info_url;
    private int discount;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
