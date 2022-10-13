package kkakka.mainservice.cart.application;

import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addOrChangeCartItem(CartRequestDto cartRequestDto, LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(KkaKkaException::new);
        Product product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(KkaKkaException::new);

        Cart cart = findOrCreateCart(member);
        CartItem cartItem = findOrCreateCartItem(product, cart);

        cartItem.changeQuantity(cartRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
        cart.add(cartItem);
        return cart.getId();
    }

    @NotNull
    private CartItem findOrCreateCartItem(Product product, Cart cart) {
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),
                        product.getId())
                .orElseGet(() -> cartItemRepository.save(
                        CartItem.create(cart, product)
                ));
        return cartItem;
    }

    public CartResponseDto showCartByMember(LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(KkaKkaException::new);

        Cart cart = findOrCreateCart(member);
        final List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), cartItemDtos);
    }

    @Transactional
    public void deleteCartItems(List<Long> cartItemIds, LoginMember loginMember) {
        Long loginMemberId = loginMember.getId();
        cartItemIds.forEach(deleteRequestId ->
                cartItemRepository.deleteByIdAndMemberId(deleteRequestId, loginMemberId));
    }

    private Cart findOrCreateCart(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> cartRepository.save(new Cart(member)));
    }
}
