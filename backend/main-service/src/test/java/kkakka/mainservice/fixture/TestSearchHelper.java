package kkakka.mainservice.fixture;

import static java.util.stream.Collectors.*;

import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.elasticsearch.application.SearchHelper;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.helper.ProductsSearchResult;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.domain.repository.ProductRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSearchHelper implements SearchHelper {

    @Autowired
   ProductRepository productRepository;
    @Autowired
    ProductRepositorySupport productRepositorySupport;

    @Override
    public ProductsSearchResult searchProductIds(SearchParamDto searchParamDto, Pageable pageable) {
        Page<Product> products = productRepository.findByKeyword(searchParamDto.getKeyword(),
            pageable);
        PageInfo pageInfo = PageInfo.from(
            products.getPageable().getPageNumber(),
            products.getTotalPages(),
            products.getPageable().getPageSize(),
            products.getTotalElements());

        return new ProductsSearchResult(products.getContent(),pageInfo,products.getTotalElements());
    }

    @Override
    public List<String> autoCompleteByKeyword(String keyword) {
        List<Product> products = productRepositorySupport.findTopNByKeyword(keyword);

        return products.stream()
            .map(product -> product.getName())
            .collect(toList());
    }
}
