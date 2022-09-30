package kkakka.mainservice.cart.ui;


import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.cart.AcceptanceTest;
import kkakka.mainservice.cart.application.CartService;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static kkakka.mainservice.cart.TestDataLoader.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

class CartAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 추가 성공")
    void testSaveCartItem() {

        //given
        CartRequestDto cartRequestDto = new CartRequestDto(1L, 1L, 1, null);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .when()
                .post("/carts")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

//    @Test
//    @DisplayName("장바구니 추가 실패")
//    void testFailSaveCartItem() {
//
//        //given
//        CartRequestDto cartRequestDto = new CartRequestDto(1L, 999L, 1, null);
//
//        //when
//        ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(cartRequestDto)
//                .when()
//                .post("/carts/cart")
//                .then()
//                .log().all().extract();
//
//        //then
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Test
    @DisplayName("장바구니 조회")
    void testShowMemberCartItemList() {

        //given
        Member member = MEMBER;
        CartResponseDto result = cartService.findAllCartItemByMember(member);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("memberId", member.getId())
                .when()
                .get("/carts/{memberId}")
                .then()
                .log().all().extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 아이템 삭제")
    void testRemoveCartItem() {

        //given
        Long cartItemId = 1L;

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("cartItemId", cartItemId)
                .when()
                .delete("/carts/{cartItemId}")
                .then()
                .log().all().extract();

        Optional<CartItem> cartItem = cartItemRepository.findById(1L);
        if (!cartItem.isPresent()) {
            System.out.println("삭제한 아이템 조회결과 : 없음! ");
        }

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}