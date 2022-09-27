package kkakka.mainservice.order.domain.repository;

import kkakka.mainservice.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
