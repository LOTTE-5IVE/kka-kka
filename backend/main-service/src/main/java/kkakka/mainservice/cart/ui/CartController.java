package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.common.auth.AuthenticationPrincipal;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.common.auth.aop.MemberOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@MemberOnly
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody CartRequestDto cartRequestDto,
                                            @AuthenticationPrincipal LoginMember loginMember) {
        Long cartId = cartService.addCartItem(cartRequestDto, loginMember);
        return ResponseEntity.created(URI.create(cartId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> showCart(
            @AuthenticationPrincipal LoginMember loginMember) {
        CartResponseDto cartResponseDto = cartService.showCartByMember(loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") List<Long> cartItemList,
                                               @AuthenticationPrincipal LoginMember loginMember) {
        cartService.deleteCartItems(cartItemList, loginMember);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{cartItemId}/{couponId}")
    public ResponseEntity<CartItemDto> applyCartCoupon(@PathVariable("cartItemId") Long cartItemId,
                                                       @PathVariable("couponId") Long couponId,
                                                       @AuthenticationPrincipal LoginMember loginMember) {
        CartItemDto cartItemDto = cartService.applyCouponCartItem(cartItemId, couponId, loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemDto);
    }

    @DeleteMapping("/{cartItemId}/{couponId}")
    public ResponseEntity<CartItemDto> cancelCartCoupon(@PathVariable("cartItemId") Long cartItemId,
                                                        @PathVariable("couponId") Long couponId,
                                                        @AuthenticationPrincipal LoginMember loginMember) {
        CartItemDto cartItemDto = cartService.cancelCouponCartItem(cartItemId, couponId, loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemDto);
    }
}
