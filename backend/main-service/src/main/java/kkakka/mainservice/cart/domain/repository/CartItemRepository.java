package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "SELECT c from CartItem c join fetch c.cart where c.cart.member.id = :memberId and c.product.id = :productId")
    CartItem findByMemberIdandProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Query(value = "SELECT c from CartItem c join fetch c.cart join fetch c.product where c.cart.id = :cartId")
    List<CartItem> findAllByMemberId(@Param("cartId") Long cartId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE CartItem c SET c.quantity = c.quantity + :quantity where c.id = :cartItemId")
    int updateCartItemQuantity(@Param("quantity") Integer quantity, @Param("cartItemId") Long cartItemId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM CartItem c WHERE c.id IN (SELECT c.id FROM CartItem c JOIN c.cart where c.id = :cartItemId and c.cart.member.id = :memberId)")
    int deleteCartItemById(@Param("cartItemId") Long cartItemId, @Param("memberId") Long memberId);
}
