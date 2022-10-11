package kkakka.mainservice.order.domain.repository;

import java.util.List;
import kkakka.mainservice.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select distinct o from Order o "
        + "join fetch o.productOrders po "
        + "join fetch po.product p "
        + "where o.member.id = :memberId "
        + "order by o.orderedAt desc")
    List<Order> findAllByMemberId(@Param("memberId") Long memberId);

}
