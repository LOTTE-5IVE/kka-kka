package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
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
    @PostMapping("")
    public ResponseEntity<String> saveCartItem(@RequestBody CartRequestDto cartRequestDto) {

        Long memberId = cartService.saveOrUpdateCartItem(cartRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{memberId}")
                .buildAndExpand(memberId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<CartResponseDto> showMemberCartItemList(@PathVariable("memberId") Long memberId
            ,@AuthenticationPrincipal LoginMember loginMember) {

        if(loginMember.isMember()){
            System.out.println("로그인 회원" + loginMember.getId());
        }
        if (loginMember.isAnonymous()){
            System.out.println("비회원" + loginMember.getId());
        }

        CartResponseDto result = cartService.findAllCartItemByMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") List<Long> cartItemList) {

        cartService.deleteCartItems(cartItemList);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
