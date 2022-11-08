package kkakka.mainservice.cart.application;

import kkakka.mainservice.cart.domain.Cart;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.cart.ui.dto.CouponDto;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.common.exception.NotOrderOwnerException;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Long addCartItem(CartRequestDto cartRequestDto, LoginMember loginMember) {
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

    @Transactional
    public CartResponseDto showCartByMember(LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(KkaKkaException::new);

        Cart cart = findOrCreateCart(member);
        final List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), cartItemDtos);
    }

    private CartItem findOrCreateCartItem(Product product, Cart cart) {
        return cartItemRepository.findByCartIdAndProductId(cart.getId(),
                        product.getId())
                .orElseGet(() -> cartItemRepository.save(
                        CartItem.create(cart, product)
                ));
    }

    @Transactional
    public void deleteCartItems(List<Long> cartItemIds, LoginMember loginMember) {
        Long loginMemberId = loginMember.getId();

        cartItemIds.forEach(cartItemId -> {
            CartItem cartItem = cartItemRepository
                    .findByIdandMemberId(cartItemId, loginMemberId)
                    .orElseThrow(NotOrderOwnerException::new);
            cartItemRepository.delete(cartItem);
        });
    }

    @Transactional
    public void emptyCart(LoginMember loginMember) {
        try {
            cartItemRepository.deleteAllByMemberId(loginMember.getId());
        } catch (Exception e) {
            log.warn("-------- CartService Error start ---------");
            log.warn("Fail to Empty CartItems: {}", e.getMessage());
            log.warn("-------- CartService Error end ---------");
        }
    }

    @Transactional
    public CartItemDto applyCouponCartItem(Long cartItemId, Long couponId, LoginMember loginMember) {
        Long loginMemberId = loginMember.getId();

        CartItem cartItem = cartItemRepository.findByIdandMemberId(cartItemId, loginMemberId).orElseThrow(KkaKkaException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(KkaKkaException::new);
        cartItem.applyCoupon(coupon);
        Integer discountedPrice = cartItem.getDiscountedPrice(coupon);

        return CartItemDto.applyCouponDto(cartItem, discountedPrice, CouponDto.toDto(coupon));
    }

    private Cart findOrCreateCart(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> cartRepository.save(new Cart(member)));
    }

    public int showCartItemCount(Long id) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        return cartItemRepository.countAllByMemberId(member.getId());
    }
}
