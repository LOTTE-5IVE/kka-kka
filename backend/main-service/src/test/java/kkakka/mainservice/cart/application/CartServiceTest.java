package kkakka.mainservice.cart.application;

import static kkakka.mainservice.fixture.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;

import kkakka.mainservice.TestContext;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.member.auth.ui.Authority;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTest extends TestContext {

    @Autowired
    private CartService cartService;

    @DisplayName("장바구니 비우기 - 성공")
    @Test
    void emptyCart_success() {
        // given
        CartRequestDto cartRequestDto = new CartRequestDto(PRODUCT_1.getId(), 1);
        Member member = MEMBER;
        LoginMember loginMember = new LoginMember(member.getId(), Authority.MEMBER);

        cartService.addCartItem(cartRequestDto, loginMember);

        // when
        // then
        Assertions.assertThatCode(
                () -> cartService.emptyCart(loginMember)
        ).doesNotThrowAnyException();
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
                () -> cartService.emptyCart(wrongLoginMember)
        ).doesNotThrowAnyException();
    }
}
