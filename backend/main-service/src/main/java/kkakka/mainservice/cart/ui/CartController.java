package kkakka.mainservice.cart.ui;

import java.net.URI;
import java.util.List;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<>
}
