package kkakka.mainservice.order.domain.repository;

import java.util.Optional;
import kkakka.mainservice.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select sum(o.totalPrice) from Order o where o.member.id = :memberId")
    Optional<Integer> findTotalPriceByMemberId(@Param(value = "memberId") Long memberId);
}
