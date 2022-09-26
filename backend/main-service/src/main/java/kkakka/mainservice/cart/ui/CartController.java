package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /* 장바구니 추가 */
    @PostMapping("/cart")
    public ResponseEntity<String> saveCartItem(@RequestBody CartRequestDto cartRequestDto) {

        cartService.saveCartItem(cartRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartResponseDto>> showMemberCartItemList() {

        /* 멤버 객체 어떻게 전달 받을지 생각해보기 */
        List<CartResponseDto> result = cartService.findAllCartItemByMember(new Member(1L, "신우주"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCartItem(@RequestBody List<CartRequestDto> cartRequestDto) {

        cartRequestDto.forEach(c -> {
            cartService.deleteCartItem(c.getCartItemId());
        });

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
