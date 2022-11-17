package kkakka.mainservice.elasticsearch.domain.repository;

import java.util.List;
import java.util.Optional;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ProductDocumentQueryBuilder {

    public Query searchProduct(SearchParamDto searchParamDto, Pageable pageable) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        query.withQuery(boolQueryBuilder
                .must(QueryBuilders.queryStringQuery(searchParamDto.getKeyword())))
            .withFilter(selectFilter("categoryid", searchParamDto.getCatecodes()));

        if (searchParamDto.isPrice()) {
            query.withQuery(boolQueryBuilder
                .filter(rangeFilter("price", searchParamDto.getMinPrice(),
                    searchParamDto.getMaxPrice())));
        }
        if (searchParamDto.isCalorie()) {
            query.withQuery(boolQueryBuilder
                .filter(rangeFilter("calorie", searchParamDto.getMinCalorie(),
                    searchParamDto.getMaxCalorie())));
        }
        if (!searchParamDto.getSortby().equals("accuracy")) {
            query.withSorts(sortByPrice(searchParamDto.getSortby()));
        }

        return query.withPageable(pageable).build();
    }

    public Query autoCompleteByKeyword(String keyword) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();

        return query.withQuery(QueryBuilders.boolQuery()
            .must(QueryBuilders.queryStringQuery(keyword))).build();
    }

    private static TermsQueryBuilder selectFilter(String field, List<Long> query) {
        return ((Optional.ofNullable(query).isEmpty()) || query.size() == 0) ? null
            : QueryBuilders.termsQuery(field, query);
    }

    private static RangeQueryBuilder rangeFilter(String field, int minVal, int maxVal) {
        return (minVal > 0 && maxVal == 0) ? QueryBuilders.rangeQuery(field).gte(minVal)
            : QueryBuilders.rangeQuery(field).gte(minVal).lte(maxVal);
    }

    private static FieldSortBuilder sortByPrice(String sorting) {
        return sorting.equals("DESC") ? SortBuilders.fieldSort("price").order(SortOrder.DESC)
            : SortBuilders.fieldSort("price").order(SortOrder.ASC);
    }
}
