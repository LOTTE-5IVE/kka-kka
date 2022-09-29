package kkakka.mainservice.order.ui;

import kkakka.mainservice.member.application.MemberService;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.ui.dto.OrderRequestDto;
import kkakka.mainservice.product.application.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;

    public OrderController(OrderService orderService, MemberService memberService, ProductService productService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.productService = productService;
    }

    @GetMapping
    public void createForm(Model model) {
    }

    @PostMapping
    public ResponseEntity<Void> order(@RequestBody OrderRequestDto orderRequestDto) throws Exception {

        Long memberId = orderService.order(orderRequestDto);

        return ResponseEntity.created(URI.create(memberId.toString())).build();

    }

}
