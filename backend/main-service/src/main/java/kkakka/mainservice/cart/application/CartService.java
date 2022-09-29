package kkakka.mainservice.cart.application;

import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public Long saveOrUpdateCartItem(CartRequestDto cartRequestDto) {

        /* 테스트 데이터 */
        Optional<Member> member = memberRepository.findById(cartRequestDto.getMemberId());

        Cart cart = findOrCreateCart(member.get());
        Optional<CartItem> originCartItem = Optional.ofNullable(
                cartItemRepository.findByMemberIdandProductId(member.get().getId(), cartRequestDto.getProductId())
        );

        // 동일 상품 이미 있으면 수량 업데이트
        if (originCartItem.isPresent()) {
            originCartItem.get().setQuantity(cartRequestDto.getQuantity());
            cartItemRepository.save(originCartItem.get());
            return member.get().getId();
        }

        /* 장바구니 아이템 추가 */
        /*
        TODO: NPE 처리하기
         */
        Optional<Product> product = productRepository.findById(cartRequestDto.getProductId());

        CartItem newCartItem = new CartItem(cart, product.get(), cartRequestDto.getQuantity());
        cartItemRepository.save(newCartItem);

        return member.get().getId();
    }

    /* 멤버 장바구니 목록 조회 */
    public List<CartResponseDto> findAllCartItemByMember(Member member) {

        List<CartItem> cartItemList = cartItemRepository.findAllByMemberId(member.getId());

        return cartItemList.stream()
                .map(CartResponseDto::from)
                .collect(Collectors.toList());
    }

    /* 장바구니 아이템 삭제 */
    @Transactional
    public boolean deleteCartItems(List<Long> cartItemList) {

        cartItemList.stream()
                .map(cartItemId -> cartItemRepository.deleteCartItemById(cartItemId))
                .collect(Collectors.toList());
        return true;
    }

    public Cart findOrCreateCart(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> createCart(member));
    }

    public Cart createCart(Member member) {
        return cartRepository.save(new Cart(member));
    }

}
