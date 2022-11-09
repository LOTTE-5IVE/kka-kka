package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.CartItem;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select ci from CartItem ci join fetch ci.cart c where c.id = :cartId and ci.product.id = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    @Query("select ci from CartItem ci join fetch ci.cart join ci.cart.member m where ci.id = :cartItemId and m.id = :memberId")
    Optional<CartItem> findByIdandMemberId(@Param("cartItemId") Long cartId, @Param("memberId") Long memberId);

    @Query("select count(ci) from CartItem ci where ci.cart.member.id = :memberId")
    int countAllByMemberId(Long memberId);
}
