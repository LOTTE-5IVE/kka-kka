package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /* 장바구니 추가 */
    @PostMapping("/cart")
    public ResponseEntity<String> saveCartItem(@RequestBody CartRequestDto cartRequestDto) {

        Long memberId = cartService.saveOrUpdateCartItem(cartRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{memberId}")
                .buildAndExpand(memberId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/cart/{memberId}")
    public ResponseEntity<List<CartResponseDto>> showMemberCartItemList(@PathVariable("memberId") Long memberId) {

        /*
        TODO: 멤버 객체 전달 받기
         */
        List<CartResponseDto> result = cartService.findAllCartItemByMember(new Member(1L, "신우주"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") List<Long> cartItemId) {

        cartItemId.forEach(c -> {
            cartService.deleteCartItem(c);
        });
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
