package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> saveCartItem(@RequestBody CartRequestDto cartRequestDto,
                                             @AuthenticationPrincipal LoginMember loginMember) {

        if (loginMember.isMember()) {
            cartService.saveOrUpdateCartItem(cartRequestDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand().toUri();

            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> showMemberCartItemList(@AuthenticationPrincipal LoginMember loginMember) {

        if (loginMember.isMember()) {
            CartResponseDto result = cartService.findAllCartItemByMemberId(loginMember.getId());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        if (loginMember.isAnonymous()) {

        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") List<Long> cartItemList,
                                               @AuthenticationPrincipal LoginMember loginMember) {
        if (loginMember.isMember()) {
            cartService.deleteCartItems(cartItemList,loginMember);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
