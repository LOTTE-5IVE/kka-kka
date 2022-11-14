package kkakka.mainservice.elasticsearch.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchParamDto {

    private String keyword;
    private String sortby;
    private List<Long> catecodes;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minCalorie;
    private Integer maxCalorie;

    public static SearchParamDto create(String keyword,
        String sortby,
        List<Long> catecodes,
        Integer minPrice,
        Integer maxPrice,
        Integer minCalorie,
        Integer maxCalorie) {
        return new SearchParamDto(keyword,
            sortby,
            catecodes,
            minPrice,
            maxPrice,
            minCalorie,
            maxCalorie);
    }

    public boolean isPrice() {
        return this.minPrice != 0 || this.maxPrice != 0;
    }

    public boolean isCalorie() {
        return this.minCalorie != 0 || this.maxCalorie != 0;
    }
}
