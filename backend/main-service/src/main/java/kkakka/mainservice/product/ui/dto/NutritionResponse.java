package kkakka.mainservice.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.product.domain.Nutrition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NutritionResponse {

    private Long id;
    private String calorie;
    private String carbohydrate;
    private String sugar;
    private String protein;
    private String fat;
    @JsonProperty(value = "saturated_fat")
    private String saturatedFat;
    @JsonProperty(value = "trans_fat")
    private String transFat;
    private String cholesterol;
    private String sodium;
    private String calcium;

    public static NutritionResponse create(Nutrition nutrition) {
        return new NutritionResponse(
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
}
