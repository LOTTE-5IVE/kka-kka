package kkakka.mainservice.product.ui.dto;

import kkakka.mainservice.category.domain.Category;
import lombok.Data;

@Data
public class ProductResponseDto {

    private Long productId;
    private String categoryName;
    private String name;
    private int price;
    private int stock;
    private String image_url;
    private String detail_image_url;
    private String nutrition_info_url;
    private int discount;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
