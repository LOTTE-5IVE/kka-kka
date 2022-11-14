package kkakka.mainservice.elasticsearch.application;

import static java.util.stream.Collectors.*;

import java.util.List;
import kkakka.mainservice.elasticsearch.application.dto.CategoryDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.ProductDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.application.dto.SearchResultDto;
import kkakka.mainservice.elasticsearch.helper.ProductsSearchResult;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductDocumentService {

    private final SearchHelper searchHelper;

    public SearchResultResponse findByKeyword(SearchParamDto searchParamDto, Pageable pageable) {
        ProductsSearchResult searchResult = searchHelper.searchProductIds(searchParamDto, pageable);
        List<ProductDocumentDto> productDtos = searchResult.getProducts().stream()
            .map(product -> ProductDocumentDto.toDto(
                product,
                CategoryDocumentDto.toDto(product.getCategory())
            ))
            .collect(toList());

        return SearchResultDto.toDto(
            searchResult.getTotalHits(),
            productDtos,
            searchResult.getPageInfo()).toResponseDto();
    }

    public List<String> autoCompleteByKeyword(String keyword) {
        return searchHelper.autoCompleteByKeyword(keyword);
    }
}