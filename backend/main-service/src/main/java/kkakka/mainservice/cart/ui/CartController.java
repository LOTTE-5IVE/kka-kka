package kkakka.mainservice.cart.ui;

import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    public ResponseEntity<String> saveCartItem(@RequestBody CartRequestDto cartRequestDto) {
        cartService.saveCartItem(cartRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }


}
