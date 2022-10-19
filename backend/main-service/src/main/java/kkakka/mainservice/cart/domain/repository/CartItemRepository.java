package kkakka.mainservice.cart.domain.repository;

import java.util.Optional;
import kkakka.mainservice.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM CartItem c WHERE c.id IN (SELECT c.id FROM CartItem c JOIN c.cart where c.id = :cartItemId and c.cart.member.id = :memberId)")
    void deleteByIdAndMemberId(@Param("cartItemId") Long cartItemId, @Param("memberId") Long memberId);

    @Query("select ci from CartItem ci join fetch ci.cart c where c.id = :cartId and ci.product.id = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
