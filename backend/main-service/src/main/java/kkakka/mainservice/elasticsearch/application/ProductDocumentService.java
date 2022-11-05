package kkakka.mainservice.elasticsearch.application;


import static java.util.stream.Collectors.*;

import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.application.dto.CategoryDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.ProductDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.application.dto.SearchResultDto;
import kkakka.mainservice.elasticsearch.domain.ProductDocument;
import kkakka.mainservice.elasticsearch.domain.repository.ProductDocumentQueryBuilder;
import kkakka.mainservice.elasticsearch.domain.repository.ProductSearchRepository;
import kkakka.mainservice.elasticsearch.helper.Indices;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import kkakka.mainservice.product.domain.repository.ProductRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductDocumentService {

    private final ProductSearchRepository productSearchRepository;
    private final ProductDocumentQueryBuilder productDocumentQueryBuilder;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ProductRepositorySupport productRepositorySupport;

    public SearchResultResponse findByKeyword(SearchParamDto searchParamDto, Pageable pageable) {

        Query query = productDocumentQueryBuilder.searchProduct(searchParamDto, pageable);

        SearchHits<ProductDocument> searchHits = elasticsearchRestTemplate.search(query,
            ProductDocument.class, IndexCoordinates.of(Indices.PRDUCT_INDEX));

        SearchPage<ProductDocument> searchPages = SearchHitSupport.searchPageFor(searchHits,
            query.getPageable());

        List<Long> productIds = searchPages.stream()
            .map(searchPage -> searchPage.getContent().getId())
            .collect(toList());

        long totalHits = searchHits.getTotalHits();
        List<ProductDocumentDto> productDtos = productRepositorySupport.findByIds(productIds)
            .stream()
            .map(product -> ProductDocumentDto.toDto(
                product,
                CategoryDocumentDto.toDto(product.getCategory())
            ))
            .collect(toList());

        final PageInfo pageInfo = PageInfo.from(
            searchPages.getPageable().getPageNumber(),
            searchPages.getTotalPages(),
            searchPages.getPageable().getPageSize(),
            searchPages.getTotalElements()
        );

        return SearchResultDto.toDto(totalHits, productDtos,
            pageInfo).toResponseDto();
    }
}
