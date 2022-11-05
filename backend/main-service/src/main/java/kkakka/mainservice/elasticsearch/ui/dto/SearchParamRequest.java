package kkakka.mainservice.elasticsearch.ui.dto;

import java.util.List;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchParamRequest {

    private String keyword;
    private String sort;
    private List<Long> catecodes;
    private int minprice;
    private int maxprice;
    private int mincalorie;
    private int maxcalorie;

    public SearchParamRequest() {
        this.sort = "accuracy";
    }


    public SearchParamDto toDto() {
        return SearchParamDto.create(keyword,
            sort,
            catecodes,
            minprice,
            maxprice,
            mincalorie,
            maxcalorie
        );
    }
}
