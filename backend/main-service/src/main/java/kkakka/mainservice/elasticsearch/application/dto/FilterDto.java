package kkakka.mainservice.elasticsearch.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterDto {

    private List<String> categoryNames;
    private Integer maxPrice;
    private Integer minPrice;
    private Integer maxCalorie;
    private Integer minCalorie;

    public static FilterDto create(List<String> categoryNames,
        Integer maxPrice,
        Integer minPrice,
        Integer maxCalorie,
        Integer minCalorie) {
        return new FilterDto(categoryNames, maxPrice, minPrice, maxCalorie, minCalorie);
    }
}
