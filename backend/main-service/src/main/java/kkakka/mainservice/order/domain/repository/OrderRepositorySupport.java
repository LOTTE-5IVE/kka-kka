package kkakka.mainservice.order.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.QOrder;
import kkakka.mainservice.order.domain.QProductOrder;
import kkakka.mainservice.product.domain.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public OrderRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Order> findByMemberId(Long memberId, Long lastId, int pageSize) {
        BooleanBuilder dynamicLtId = new BooleanBuilder();
        if (lastId != null) {
            dynamicLtId.and(QOrder.order.id.lt(lastId));
        }

        return jpaQueryFactory
                .selectFrom(QOrder.order)
                .leftJoin(QOrder.order.productOrders, QProductOrder.productOrder)
                .fetchJoin()
                .leftJoin(QProductOrder.productOrder.product, QProduct.product)
                .fetchJoin()
                .where(QOrder.order.member.id.eq(memberId))
                .where(dynamicLtId)
                .orderBy(QOrder.order.id.desc())
                .limit(pageSize)
                .fetch();
    }
}
