package kkakka.mainservice.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public Long getCategoryId() {
        return categoryDto.getId();
    }

    public String getCategoryName() {
        return categoryDto.getName();
    }

    public Long getNutritionId() {
        return nutritionDto.getId();
    }

    public String getCalorie() {
        return nutritionDto.getCalorie();
    }

    public String getCarbohydrate() {
        return nutritionDto.getCarbohydrate();
    }

    public String getSugar() {
        return nutritionDto.getSugar();
    }

    public String getProtein() {
        return nutritionDto.getProtein();
    }

    public String getFat() {
        return nutritionDto.getFat();
    }

    public String getSaturatedFat() {
        return nutritionDto.getSaturatedFat();
    }

    public String getTransFat() {
        return nutritionDto.getTransFat();
    }

    public String getCholesterol() {
        return nutritionDto.getCholesterol();
    }

    public String getSodium() {
        return nutritionDto.getSodium();
    }

    public String getCalcium() {
        return nutritionDto.getCalcium();
    }
}