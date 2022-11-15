package kkakka.mainservice.product.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kkakka.mainservice.DataLoader;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.QCategory;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ProductRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Product.class);
        this.queryFactory = jpaQueryFactory;
    }

    public Page<Product> findAllByCategoryWithSort(
            Optional<Long> categoryId,
            String sortBy,
            Pageable pageable
    ) {
        final BooleanBuilder builder = new BooleanBuilder();
        categoryId.ifPresent((cId) -> {
            if (isSavedCategoryId(cId)) {
                builder.and(QCategory.category.id.eq(cId));
            }
        });

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

        final JPAQuery<Long> countQuery = queryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .where(
                        builder
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private boolean isSavedCategoryId(Long cId) {
        return DataLoader.categories.values().stream()
                .mapToLong(Category::getId)
                .anyMatch(cId::equals);
    }

    private OrderSpecifier<?> orderWithSortBy(String sortBy) {
        if ("BEST".equals(sortBy)) {
            return new OrderSpecifier<Double>(Order.DESC, QProduct.product.ratingAvg);
        }

        if ("OLD".equals(sortBy)) {
            return new OrderSpecifier<Long>(Order.ASC, QProduct.product.id);
        }

        if("DESC".equals(sortBy)) {
            return new OrderSpecifier<>(Order.DESC, QProduct.product.price);
        }

        if("ASC".equals(sortBy)) {
            return new OrderSpecifier<>(Order.ASC, QProduct.product.price);
        }

        return new OrderSpecifier<Long>(Order.DESC, QProduct.product.id);
    }

    public List<Product> findTopNByKeyword(String keyword) {
        return queryFactory.selectFrom(QProduct.product)
            .where(QProduct.product.name.contains(keyword))
            .limit(10).fetch();
    }
}
