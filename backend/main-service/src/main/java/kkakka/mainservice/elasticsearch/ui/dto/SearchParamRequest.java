package kkakka.mainservice.elasticsearch.ui.dto;

import java.util.List;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamRequest {

    private String keyword;
    private String sortBy;
    private List<Long> catecodes;
    private Long categoryId;
    private int minprice;
    private int maxprice;
    private int mincalorie;
    private int maxcalorie;

    public SearchParamDto toDto() {
        return SearchParamDto.create(keyword,
            sortBy,
            catecodes,
            minprice,
            maxprice,
            mincalorie,
            maxcalorie
        );
    }

    public boolean isKeyword() {
        return keyword != null;
    }
}
