package kkakka.mainservice.order.domain.repository;

import kkakka.mainservice.order.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("select po from ProductOrder po " +
            "join fetch po.order o " +
            "join  po.order.member m " +
            "where m.id = :memberId and po.id = :productOrderId")
    Optional<ProductOrder> findByIdAndMemberId(@Param("productOrderId") Long id, @Param("memberId") Long memberId);
}
