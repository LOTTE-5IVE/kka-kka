package kkakka.mainservice.member.member.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.common.dto.NoOffsetPageInfo;
import kkakka.mainservice.common.dto.PageableNoOffsetResponse;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.application.dto.MemberUpdateDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.ui.dto.MemberInfoRequest;
import kkakka.mainservice.member.member.ui.dto.MemberResponse;
import kkakka.mainservice.member.member.ui.dto.OrderResponse;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.application.dto.MemberOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@MemberOnly
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service";
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMemberInfo(
            @AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.showInfo(loginMember.getId());
        return ResponseEntity.ok(member.toDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> editMemberInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        memberService.updateMember(MemberUpdateDto.create(loginMember.getId(), memberInfoRequest));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/orders")
    public ResponseEntity<PageableNoOffsetResponse<List<OrderResponse>>> showMyOrders(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(required = false) Long orderId,
            @RequestParam(defaultValue = "6") int pageSize
    ) {
        final List<MemberOrderDto> memberOrderDtos = orderService.showMemberOrders(
                loginMember.getId(),
                orderId, pageSize);
        final Long lastOrderIdInList = memberOrderDtos.get(memberOrderDtos.size() - 1).getId();

        final boolean isLastOrder = orderService.checkIsLastOrder(
                loginMember.getId(),
                lastOrderIdInList
        );

        final NoOffsetPageInfo pageInfo = NoOffsetPageInfo.from(
                lastOrderIdInList,
                isLastOrder,
                pageSize,
                memberOrderDtos.size()
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                PageableNoOffsetResponse.from(
                        memberOrderDtos.stream()
                                .map(MemberOrderDto::toResponseDto)
                                .collect(Collectors.toList()), pageInfo)
        );
    }

    @GetMapping("/me/orders/all")
    public ResponseEntity<Map<String, Integer>> showOrderCount(
            @AuthenticationPrincipal LoginMember loginMember) {
        final int orderCount = orderService.showMemberOrderCount(loginMember.getId());
        final Map<String, Integer> result = new HashMap<>();
        result.put("orderCount", orderCount);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/me/carts/all")
    public ResponseEntity<Map<String, Integer>> showCartCount(
            @AuthenticationPrincipal LoginMember loginMember) {
        final int cartItemCount = cartService.showCartItemCount(loginMember.getId());
        final Map<String, Integer> result = new HashMap<>();
        result.put("cartCount", cartItemCount);
        return ResponseEntity.ok().body(result);
    }
}
