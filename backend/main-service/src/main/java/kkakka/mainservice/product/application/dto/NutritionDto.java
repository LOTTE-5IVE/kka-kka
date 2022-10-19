package kkakka.mainservice.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
