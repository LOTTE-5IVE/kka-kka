package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
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

    @GetMapping("/{memberId}")
    public ResponseEntity<CartResponseDto> showMemberCartItemList(@PathVariable("memberId") Long memberId) {

        /*
        TODO: 멤버 객체 전달 받기
         */
        CartResponseDto result = cartService.findAllCartItemByMember(new Member(1L, "신우주"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") List<Long> cartItemList) {

        cartService.deleteCartItems(cartItemList);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
