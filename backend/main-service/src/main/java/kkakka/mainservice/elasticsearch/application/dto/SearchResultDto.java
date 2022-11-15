package kkakka.mainservice.elasticsearch.application.dto;

import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResultDto {

    private Long totalHits;
    private List<ProductDocumentDto> productDocumentDtos;
    private PageInfo pageInfo;

    public static SearchResultDto toDto(Long totalHits,
        List<ProductDocumentDto> productDocumentDtos, PageInfo pageInfo) {
        return new SearchResultDto(totalHits,
            productDocumentDtos,
            pageInfo
        );
    }

    public static SearchResultResponse toResponseDto(Long totalHits,
        List<ProductDocumentDto> productDocumentDtos, PageInfo pageInfo) {
        return new SearchResultResponse(
            totalHits,
            pageInfo,
            productDocumentDtos
        );
    }
}
