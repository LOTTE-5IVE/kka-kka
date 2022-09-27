package kkakka.mainservice.cart.ui;


import static kkakka.mainservice.cart.TestDataLoader.MEMBER;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.cart.AcceptanceTest;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CartAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartService cartService;

    @Order(1)
    @DisplayName("장바구니 추가")
    @Test
    void saveCartItem() {
        IntStream.rangeClosed(1,5).forEach(
                i -> {
                    // given
                    CartRequestDto cartRequestDto = new CartRequestDto(1L, (long)i, 1, null);
        //when
                    ExtractableResponse<Response> response = RestAssured.given().log().all()
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .body(cartRequestDto)
                            .when()
                            .post("/carts/cart")
                            .then()
                            .log().all().extract();
        //then
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                }
        );
    }

    @Order(2)
    @DisplayName("장바구니 조회")
    @Test
    void showMemberCartItemList() {

        //given
        Member member = MEMBER;
        List<CartResponseDto> result = cartService.findAllCartItemByMember(member);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts/cart")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
//
//    @Test
//    void removeCartItem() {
//    }

}