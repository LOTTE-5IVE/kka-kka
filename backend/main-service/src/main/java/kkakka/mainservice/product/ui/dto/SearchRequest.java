package kkakka.mainservice.product.ui.dto;

import kkakka.mainservice.product.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private String keyword;

    public SearchDto toDto() {
        return SearchDto.create(keyword);
    }

}
