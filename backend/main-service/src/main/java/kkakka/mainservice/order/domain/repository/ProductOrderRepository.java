package kkakka.mainservice.order.domain.repository;

import kkakka.mainservice.order.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {
}
