package kkakka.mainservice.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calorie;
    private String carbohydrate;
    private String sugar;
    private String protein;
    private String fat;
    @Column(name = "saturated_fat")
    private String saturatedFat;
    @Column(name = "trans_fat")
    private String transFat;
    private String cholesterol;
    private String sodium;
    private String calcium;

    public Nutrition(String calorie, String carbohydrate, String sugar, String protein, String fat,
            String saturatedFat, String transFat, String cholesterol, String sodium,
            String calcium) {
        this(null, calorie, carbohydrate, sugar, protein, fat, saturatedFat, transFat, cholesterol,
                sodium, calcium);
    }
}
