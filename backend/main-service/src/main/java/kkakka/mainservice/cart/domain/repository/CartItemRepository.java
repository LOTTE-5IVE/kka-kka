package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "SELECT c from CartItem c join fetch c.cart where c.cart.member.id = :memberId and c.product.id = :productId")
    CartItem findByMemberIdandProductId(@Param("memberId") Long memberId, @Param("productId") Long ProductId);
}
