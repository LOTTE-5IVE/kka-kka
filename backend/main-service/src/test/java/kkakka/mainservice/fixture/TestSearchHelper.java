package kkakka.mainservice.fixture;

import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.application.SearchHelper;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.helper.ProductsSearchResult;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSearchHelper implements SearchHelper {

    private final ProductRepository productRepository;

    public TestSearchHelper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductsSearchResult searchProductIds(SearchParamDto searchParamDto, Pageable pageable) {
        Page<Product> products = productRepository.findByKeyword(searchParamDto.getKeyword(),
            pageable);
        System.out.println("products.getTotalElements() = " + products.getTotalElements());
        PageInfo pageInfo = PageInfo.from(
            products.getPageable().getPageNumber(),
            products.getTotalPages(),
            products.getPageable().getPageSize(),
            products.getTotalElements());

        return new ProductsSearchResult(products.getContent(),pageInfo,products.getTotalElements());
    }
}
