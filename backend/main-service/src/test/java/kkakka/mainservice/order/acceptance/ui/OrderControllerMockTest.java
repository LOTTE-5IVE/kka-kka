package kkakka.mainservice.order.acceptance.ui;

import static kkakka.mainservice.fixture.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.common.auth.Authority;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.OrderController;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.RecipientRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderControllerMockTest extends TestContext {

    @DisplayName("상품 주문 시 장바구니 비우기 동작 - 성공")
    @Test
    void emptyCartIfOrder_success() {
        // given
        OrderService mockOrderService = mock(OrderService.class);
        CartService mockCartService = mock(CartService.class);
        final InOrder inOrder = inOrder(mockOrderService, mockCartService);

        OrderController orderController = new OrderController(mockOrderService, mockCartService);

        Member member = MEMBER;
        RecipientRequest recipientRequest = new RecipientRequest(member.getName(),
                member.getEmail(), member.getPhone(),
                member.getAddress());
        OrderRequest orderRequest = new OrderRequest(
                recipientRequest,
                List.of(new ProductOrderDto(PRODUCT_1.getId(), 1))
        );

        LoginMember loginMember = new LoginMember(member.getId(), Authority.MEMBER);

        // when
        orderController.order(loginMember, orderRequest);

        // then
        inOrder.verify(mockOrderService, times(1)).order(any());
        inOrder.verify(mockCartService, times(1)).emptyCart(loginMember);
    }
}
