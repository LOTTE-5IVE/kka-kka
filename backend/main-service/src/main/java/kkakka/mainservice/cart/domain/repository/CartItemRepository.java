package kkakka.mainservice.cart.domain.repository;

import kkakka.mainservice.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
