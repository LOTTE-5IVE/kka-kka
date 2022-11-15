package kkakka.mainservice.elasticsearch.helper;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.elasticsearch.application.SearchHelper;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.domain.ProductDocument;
import kkakka.mainservice.elasticsearch.domain.repository.ProductDocumentQueryBuilder;
import kkakka.mainservice.elasticsearch.domain.repository.ProductSearchRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("!test")
public class ElasticSearchHelper implements SearchHelper {

    private final ProductSearchRepository productSearchRepository;
    private final ProductRepository productRepository;
    private final ProductDocumentQueryBuilder productDocumentQueryBuilder;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public ProductsSearchResult searchProductIds(SearchParamDto searchParamDto, Pageable pageable) {
        Query query = productDocumentQueryBuilder.searchProduct(searchParamDto, pageable);

        SearchHits<ProductDocument> searchHits = elasticsearchRestTemplate.search(query,
            ProductDocument.class, IndexCoordinates.of(Indices.PRDUCT_INDEX));
        if (!searchHits.hasSearchHits()) {
            return new ProductsSearchResult(new ArrayList<>(),
                PageInfo.from(pageable.getPageNumber(), 0, pageable.getPageSize(), 0),
                searchHits.getTotalHits());
        }

        SearchPage<ProductDocument> searchPages = SearchHitSupport.searchPageFor(searchHits,
            query.getPageable());

        List<Long> productIds = searchPages.stream()
            .map(searchPage -> searchPage.getContent().getId()).collect(toList());

        List<Product> products = productRepository.findAllById(productIds);

        return new ProductsSearchResult(sortProductBySearchHit(productIds, products),
            createPageInfo(searchPages), searchHits.getTotalHits());
    }

    @Override
    public List<String> autoCompleteByKeyword(String keyword) {
        Query query = productDocumentQueryBuilder.autoCompleteByKeyword(keyword);

        SearchHits<ProductDocument> searchHits = elasticsearchRestTemplate.search(query,
            ProductDocument.class, IndexCoordinates.of(Indices.PRDUCT_INDEX));

        return searchHits.stream()
            .map(productDocumentSearchHit -> productDocumentSearchHit.getContent().getName())
            .collect(toList());
    }

    private PageInfo createPageInfo(SearchPage<ProductDocument> searchPages) {
        return PageInfo.from(searchPages.getPageable().getPageNumber(), searchPages.getTotalPages(),
            searchPages.getPageable().getPageSize(), searchPages.getTotalElements());
    }

    private List<Product> sortProductBySearchHit(List<Long> productIds, List<Product> products) {
        List<Product> sortedProducts = new ArrayList<>();

        for (Long id : productIds) {
            sortedProducts.add(
                products.stream().filter(product -> id.equals(product.getId())).findAny()
                    .orElseThrow());
        }

        return sortedProducts;
    }
}
