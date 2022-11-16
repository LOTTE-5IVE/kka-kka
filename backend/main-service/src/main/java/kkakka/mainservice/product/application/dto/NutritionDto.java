package kkakka.mainservice.product.application.dto;

import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.ui.dto.NutritionResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NutritionDto {

    private Long id;
    private String calorie;
    private String carbohydrate;
    private String sugar;
    private String protein;
    private String fat;
    private String saturatedFat;
    private String transFat;
    private String cholesterol;
    private String sodium;
    private String calcium;

    public static NutritionDto toDto(Nutrition nutrition) {
        return new NutritionDto(
                nutrition.getId(),
                nutrition.getCalorie(),
                nutrition.getCarbohydrate(),
                nutrition.getSugar(),
                nutrition.getProtein(),
                nutrition.getFat(),
                nutrition.getSaturatedFat(),
                nutrition.getTransFat(),
                nutrition.getCholesterol(),
                nutrition.getSodium(),
                nutrition.getCalcium()
        );
    }

    public NutritionResponse toResponseDto() {
        return new NutritionResponse(
                this.id,
                this.calorie,
                this.carbohydrate,
                this.sugar,
                this.protein,
                this.fat,
                this.saturatedFat,
                this.transFat,
                this.cholesterol,
                this.sodium,
                this.calcium
        );
    }

    public static NutritionDto createEmptyDto(){
        return new NutritionDto(
                0L,"","","","","","","","","",""
        );
    }
}

