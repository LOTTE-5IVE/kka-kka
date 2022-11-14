package kkakka.mainservice.product.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kkakka.mainservice.category.domain.QCategory;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.QProduct;
import kkakka.mainservice.product.domain.SearchWords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ProductRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Product.class);
        this.queryFactory = jpaQueryFactory;
    }

    public Page<Product> findBySearch(SearchWords searchWords, Pageable pageable) {
        final List<Product> result = queryFactory.selectFrom(QProduct.product)
                .leftJoin(QProduct.product.category, QCategory.category)
                .fetchJoin()
                .where(
                        categoryNameEq(searchWords)
                                .or(productNameLike(searchWords))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    public List<Product> findTopNByKeyword(String keyword) {
        return queryFactory.selectFrom(QProduct.product)
            .where(QProduct.product.name.contains(keyword))
            .limit(10).fetch();
    }

    private BooleanBuilder categoryNameEq(SearchWords searchWords) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (searchWords.hasSearchWords()) {
            for (String searchWord : searchWords.getSearchWords()) {
                builder.or(QProduct.product.category.name.likeIgnoreCase("%" + searchWord + "%"));
            }
        }
        return builder;
    }

    private BooleanBuilder productNameLike(SearchWords searchWords) {
        final BooleanBuilder builder = new BooleanBuilder();
        if (searchWords.hasSearchWords()) {
            for (String searchWord : searchWords.getSearchWords()) {
                builder.or(QProduct.product.name.likeIgnoreCase("%" + searchWord + "%"));
            }
        }
        return builder;
    }
}
