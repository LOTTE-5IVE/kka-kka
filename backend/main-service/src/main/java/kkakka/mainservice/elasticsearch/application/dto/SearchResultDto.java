package kkakka.mainservice.elasticsearch.application.dto;

import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {

    private Long totalHits;
    private List<ProductDocumentDto> productDocumentDtos;
    private PageInfo pageInfo;

    public static SearchResultDto toDto(Long totalHits, List<ProductDocumentDto> productDocumentDtos,PageInfo pageInfo) {
        return new SearchResultDto(totalHits,
            productDocumentDtos,
            pageInfo
        );
    }
    public SearchResultResponse toResponseDto() {
        return new SearchResultResponse(
            this.totalHits,
            this.pageInfo,
            this.productDocumentDtos
        );
    }
}
