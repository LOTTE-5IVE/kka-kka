package kkakka.mainservice.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    private int discount;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryResponse {

        private Long id;
        private String name;
    }
}
