package kkakka.mainservice.cart.ui;


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

import java.util.ArrayList;
import java.util.List;

import static kkakka.mainservice.cart.TestDataLoader.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CartAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니 추가")
    @Order(1)
    void testSaveCartItem() {

        CartRequestDto cartRequestDto = new CartRequestDto(1L, 1L, 1, null);
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



    @Test
    @DisplayName("장바구니 조회")
    @Order(2)
    void testShowMemberCartItemList() {

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
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 아이템 삭제")
    @Order(3)
    void testRemoveCartItem() {

        //given
        List<Long> cartItemId = new ArrayList<>();
        cartItemId.add(1L);
        cartItemId.add(2L);
        cartItemId.add(3L);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("cartItemId", cartItemId)
                .when()
                .delete("/carts/cart")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}