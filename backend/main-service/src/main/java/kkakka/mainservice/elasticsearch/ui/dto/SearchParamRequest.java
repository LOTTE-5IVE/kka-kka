package kkakka.mainservice.elasticsearch.ui.dto;

import java.net.URI;
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
    private String sortby;
    private List<Long> catecodes;
    private int minprice;
    private int maxprice;
    private int mincalorie;
    private int maxcalorie;

    public SearchParamDto toDto() {
        return SearchParamDto.create(keyword,
            sortby,
            catecodes,
            minprice,
            maxprice,
            mincalorie,
            maxcalorie
        );
    }
}
