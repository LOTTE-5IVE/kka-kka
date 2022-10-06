package kkakka.mainservice.cart.application;

import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveOrUpdateCartItem(CartRequestDto cartRequestDto) {

        /* 테스트 데이터 */
        Member member = memberRepository.findById(cartRequestDto.getMemberId())
                .orElseThrow(IllegalArgumentException::new);

        Cart cart = findOrCreateCart(member);
        Optional<CartItem> originCartItem = Optional.ofNullable(
                cartItemRepository.findByMemberIdandProductId(member.getId(), cartRequestDto.getProductId())
        );

        // 동일 상품 이미 있으면 수량 업데이트
        if (originCartItem.isPresent()) {
            originCartItem.get().setQuantity(cartRequestDto.getQuantity());
            cartItemRepository.save(originCartItem.get());
            return member.getId();
        }

        /* 장바구니 아이템 추가 */
        Product product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(IllegalArgumentException::new);
        CartItem newCartItem = new CartItem(cart, product, cartRequestDto.getQuantity());
        cartItemRepository.save(newCartItem);

        return member.getId();
    }

    public CartResponseDto findAllCartItemByMemberId(Long memberId) {

        Optional<Cart> cart = cartRepository.findByMemberId(memberId);
        if (cart.isEmpty()) {
            return new CartResponseDto(0L, Collections.emptyList());
        }

        Long cartId = cart.get().getId();

        List<CartItem> cartItemList = cartItemRepository.findAllByMemberId(cartId);
        List<CartItemDto> memberCartItems = cartItemList.stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());

        return new CartResponseDto(cartId, memberCartItems);
    }

    @Transactional
    public void deleteCartItems(List<Long> cartItemIds, LoginMember loginMember) {

        Long loginMemberId = loginMember.getId();
        cartItemIds.forEach(deleteRequestId ->
                cartItemRepository.deleteCartItemById(deleteRequestId, loginMemberId));
    }

    public Cart findOrCreateCart(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> createCart(member));
    }

    public Cart createCart(Member member) {
        return cartRepository.save(new Cart(member));
    }

}
