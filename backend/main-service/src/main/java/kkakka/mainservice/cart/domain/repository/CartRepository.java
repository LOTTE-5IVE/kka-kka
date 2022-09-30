package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.member.id = :memberId")
    Optional<Cart> findByMemberId(@Param("memberId") Long memberId);
}
