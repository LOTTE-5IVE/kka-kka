package kkakka.mainservice.elasticsearch.application;

import java.util.List;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.helper.ProductsSearchResult;
import org.springframework.data.domain.Pageable;

public interface SearchHelper {

    ProductsSearchResult searchProductIds(SearchParamDto searchParamDto, Pageable pageable);

    List<String> autoCompleteByKeyword(String keyword);
}
