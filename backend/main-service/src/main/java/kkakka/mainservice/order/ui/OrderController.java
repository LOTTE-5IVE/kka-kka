package kkakka.mainservice.order.ui;

import java.net.URI;
import java.util.List;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@MemberOnly
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
    public ResponseEntity<Void> order(@AuthenticationPrincipal LoginMember loginMember,
            @RequestBody OrderRequest orderRequest) {
        Long orderId = orderService.order(OrderDto.create(loginMember.getId(), orderRequest));
        return ResponseEntity.created(URI.create(orderId.toString())).build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> findOrders(
            @AuthenticationPrincipal LoginMember loginMember) {
        List<OrderResponse> memberOrders = orderService.findMemberOrders(loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(memberOrders);
    }
}
