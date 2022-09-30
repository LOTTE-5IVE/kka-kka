package kkakka.mainservice.order.ui;

import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public void showOrderFormInfo(
        //TODO: memberId -> 회원 정보랑 장바구니 목록을 전부 return 해줌
    ) {
    }

    @PostMapping
    public ResponseEntity<Void> order(@RequestBody OrderRequest orderRequest) throws Exception {
        Long memberId = orderService.order(orderRequest);

        return ResponseEntity.created(URI.create(memberId.toString())).build();
    }

    @GetMapping("{memberId}")
    public ResponseEntity<List<OrderResponse>> findOrders(@PathVariable Long memberId) {
        List<OrderResponse> memberOrders = orderService.findMemberOrders(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberOrders);
    }
}
