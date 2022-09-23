package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberId(Long id);

}
