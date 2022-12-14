package kkakka.mainservice.order.domain.repository;

import java.util.List;
import java.util.Optional;
import kkakka.mainservice.order.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o where o.member.id = :memberId order by o.orderedAt desc",
            countQuery = "select count(o) from Order o where o.member.id = :memberId order by o.orderedAt desc")
    List<Order> findAllByMemberId(@Param(value = "memberId") Long memberId, Pageable pageable);

    int countAllByMemberId(Long memberId);

    @Query("select sum(o.totalPrice) from Order o where o.member.id = :memberId")
    Optional<Integer> findTotalPriceByMemberId(@Param(value = "memberId") Long memberId);

    @Query("select o from Order o join fetch o.productOrders po join fetch po.product where o.id = :id")
    Optional<Order> findById(Long id);
}
