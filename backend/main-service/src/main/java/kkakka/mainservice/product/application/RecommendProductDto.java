package kkakka.mainservice.product.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendProductDto {

    private Long id;
    @JsonProperty("category")
    private RecommendCategoryDto recommendCategoryDto;
    private String name;
    private Integer price;
    private Integer stock;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("detail_image_url")
    private String detailImageUrl;
    @JsonProperty("nutrition_info_url")
    private String nutritionInfoUrl;
    private int discount;
    @JsonProperty("rating_avg")
    private double ratingAvg;

    public Long getCategoryId() {
        return this.recommendCategoryDto.id;
    }

    public String getCategoryName() {
        return this.recommendCategoryDto.name;
    }

    @Getter
    @NoArgsConstructor
    static class RecommendCategoryDto {

        private Long id;
        private String name;
    }
}
