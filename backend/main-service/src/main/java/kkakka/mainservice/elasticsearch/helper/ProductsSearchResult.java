package kkakka.mainservice.elasticsearch.helper;

import java.util.List;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductsSearchResult {

    private List<Product> products;
    private PageInfo pageInfo;
    private Long totalHits;
}
