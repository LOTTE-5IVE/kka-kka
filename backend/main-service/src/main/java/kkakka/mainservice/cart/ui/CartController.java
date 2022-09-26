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

    @PostMapping("/cart")
    public ResponseEntity<String> saveCartItem(@RequestBody CartRequestDto cartRequestDto) {
        cartService.saveCartItem(cartRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartResponseDto>> showMemberCartItemList(){
        List<CartResponseDto> result = cartService.findAllCartItemByMember(new Member(1L,"신우주"));


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
