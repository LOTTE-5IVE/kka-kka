package kkakka.mainservice.cart.application;

import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public String saveCartItem(CartRequestDto cartItem) {

        /* 로그인 상태 체크 */

        /* 장바구니 존재 유무 체크 ex) 장바구니에 담아둔 아이템 있는지 */
        Cart cart = new Cart();
//        cart = cartRepository.findByMemberId(cartItem.getMemberId());
        if (cart == null) {
//            cartRepository.save(member)
        }

        /* 현재 장바구니에 동일한 상품 있는지 체크 */

        /* 장바구니 아이템 추가 */
        Optional<Product> product = productRepository.findById(cartItem.getProductId());
//        cartItemRepository.save(member,product.get(),)

        return "";
    }

}
