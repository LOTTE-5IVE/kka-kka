package kkakka.mainservice.cart.application;

import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository
            , MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public String saveCartItem(CartRequestDto cartItem) {

        /* 테스트 데이터 */
        Optional<Member> member = memberRepository.findById(cartItem.getMemberId());

        /* 로그인 상태 체크 */

        /* 장바구니 존재 유무 체크 ex) 장바구니에 담아둔 아이템 있는지 */
        Cart cart = new Cart();
        cart = cartRepository.findByMemberId(cartItem.getMemberId());
        if (cart == null) {
            cartRepository.save(new Cart(member.get()));
            cart = cartRepository.findByMemberId(cartItem.getMemberId());
        }

        /* 현재 장바구니에 동일한 상품 있는지 체크 */
        CartItem item = cartItemRepository.findByMemberIdandProductId(member.get().getId(), cartItem.getProductId()); // Test용 Member
//        System.out.println(item.toString());

        /* 장바구니 아이템 추가 */
        Optional<Product> product = productRepository.findById(cartItem.getProductId());

        cartItemRepository.save(new CartItem(cart, product.get(), cartItem.getQuantity()));

        return "";
    }

}
