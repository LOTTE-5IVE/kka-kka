package kkakka.mainservice.elasticsearch.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterResponse {

    @JsonProperty("categories")
    private List<CategoryResponse> categoryResponses;

    //TODO: 추후 필터 추가 예정

}
