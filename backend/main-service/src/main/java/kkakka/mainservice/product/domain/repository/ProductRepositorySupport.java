package kkakka.mainservice.product.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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

    public Page<Product> findAllByCategoryWithSort(
            Optional<Long> categoryId,
            String sortBy,
            SearchWords searchWords,
            Pageable pageable
    ) {
        final BooleanBuilder builder = new BooleanBuilder();
        categoryId.ifPresent((cId) -> {
            builder.and(QCategory.category.id.eq(cId));
        });
        builder.and(categoryNameEq(searchWords).or(productNameLike(searchWords)));

        final OrderSpecifier<?> orderSpecifier = orderWithSortBy(sortBy);

        final List<Product> result = queryFactory.selectFrom(QProduct.product)
                .leftJoin(QProduct.product.category, QCategory.category)
                .fetchJoin()
                .where(
                        builder
                )
                .orderBy(
                        orderSpecifier
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private OrderSpecifier<?> orderWithSortBy(String sortBy) {
        if ("BEST".equals(sortBy)) {
            return new OrderSpecifier<Double>(Order.DESC, QProduct.product.ratingAvg);
        }

        if ("OLD".equals(sortBy)) {
            return new OrderSpecifier<Long>(Order.ASC, QProduct.product.id);
        }

        return new OrderSpecifier<Long>(Order.DESC, QProduct.product.id);
    }

    public Page<Product> findAllByCategoryIdOrderByRatingAvg(Optional<Long> categoryId,
            Pageable pageable) {
        final BooleanBuilder builder = new BooleanBuilder();
        categoryId.ifPresent((cId) -> {
            builder.and(QCategory.category.id.eq(cId));
        });

        final List<Product> result = queryFactory.selectFrom(QProduct.product)
                .leftJoin(QProduct.product.category, QCategory.category)
                .fetchJoin()
                .where(builder)
                .orderBy(QProduct.product.ratingAvg.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
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
