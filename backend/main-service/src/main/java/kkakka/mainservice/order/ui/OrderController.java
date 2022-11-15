package kkakka.mainservice.order.ui;

import java.net.URI;
import java.util.List;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderWithCouponDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@MemberOnly
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> order(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody OrderRequest orderRequest
    ) {
        Long orderId = orderService.order(
            OrderDto.create(
                loginMember.getId(),
                orderRequest.toRecipientDto(),
                orderRequest
            ));
        cartService.emptyCart(loginMember);
        return ResponseEntity.created(URI.create(orderId.toString())).build();
    }

    @DeleteMapping("/{productOrderId}")
    public ResponseEntity<Void> requestOrdersCancel(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable("productOrderId") List<Long> productOrderIdList
    ) {
        orderService.cancelOrder(productOrderIdList, loginMember);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<ProductOrderWithCouponDto> applyProductCoupon(
        @PathVariable("couponId") Long couponId,
        @RequestBody ProductOrderDto productOrderDto,
        @AuthenticationPrincipal LoginMember loginMember) {
        ProductOrderWithCouponDto productOrderWithCouponDto = orderService.applyProductCoupon(
            loginMember.getId(), productOrderDto, couponId);

        return ResponseEntity.status(HttpStatus.OK).body(productOrderWithCouponDto);

    }
}
