package kkakka.mainservice.order.ui;

import kkakka.mainservice.member.application.MemberService;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.product.application.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;

    public OrderController(OrderService orderService, MemberService memberService, ProductService productService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.productService = productService;
    }

    // 주문 폼 이동
    // 주문화면에 주문할 고객정보와 상품 정보를 넘기기 위해 model 객체에 담아서 넘겨준다.
    @GetMapping
    public void createForm(Model model) {

    }

    //주문
    // 주문 상품과 수량을 선택 후 버튼을 누르면 실행
    @PostMapping
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId, @RequestParam("count") int count) {

        Long order = orderService.order(memberId, productId, count);

        return "주문저장 성공"+order;

    }

}
