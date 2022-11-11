package kkakka.mainservice.elasticsearch.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.application.dto.ProductDocumentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultResponse {

    private Long totalHits;
    private PageInfo pageInfo;
    @JsonProperty("data")
    private List<ProductDocumentDto> productResponses;
}
