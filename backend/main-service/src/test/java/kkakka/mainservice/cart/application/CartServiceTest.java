package kkakka.mainservice.cart.application;

import static kkakka.mainservice.fixture.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.common.auth.Authority;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.order.application.OrderService;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.application.dto.RecipientDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.RecipientRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
@SpringBootTest
public class CartServiceTest extends TestContext {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @DisplayName("장바구니 비우기 - 성공")
    @Test
    void emptyCart_success() {
        // given
        CartRequestDto cartRequestDto1 = new CartRequestDto(PRODUCT_1.getId(), 1);
        CartRequestDto cartRequestDto2 = new CartRequestDto(PRODUCT_2.getId(), 1);
        Member member = MEMBER;
        LoginMember loginMember = new LoginMember(member.getId(), Authority.MEMBER);

        cartService.addCartItem(cartRequestDto1, loginMember);
        cartService.addCartItem(cartRequestDto2, loginMember);
        assertThat(cartService.showCartItemCount(loginMember.getId())).isEqualTo(2);

        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), null, 2);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);
        OrderRequest orderRequest = new OrderRequest(
                new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()),
                productOrderDtos
        );

        RecipientDto recipientDto = orderRequest.toRecipientDto();

        Long orderId = orderService.order(
                OrderDto.create(loginMember.getId(), recipientDto, orderRequest)
        );

        // when
        cartService.emptyCart(loginMember, orderId);

        // then
        assertThat(cartService.showCartItemCount(loginMember.getId())).isEqualTo(1);
    }

    @DisplayName("장바구니 비우기 - 실패(에러 없음)")
    @Test
    void emptyCart_fail() {
        // given
        CartRequestDto cartRequestDto = new CartRequestDto(PRODUCT_1.getId(), 1);
        Member member = MEMBER;
        LoginMember loginMember = new LoginMember(member.getId(), Authority.MEMBER);

        cartService.addCartItem(cartRequestDto, loginMember);

        // when
        LoginMember wrongLoginMember = new LoginMember(member.getId() + 1, Authority.MEMBER);

        // then
        Assertions.assertThatCode(
                () -> cartService.emptyCart(wrongLoginMember, null)
        ).doesNotThrowAnyException();
    }
}
