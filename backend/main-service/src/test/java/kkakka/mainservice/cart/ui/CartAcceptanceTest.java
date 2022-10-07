package kkakka.mainservice.cart.ui;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.cart.domain.CartItem;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static kkakka.mainservice.cart.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

class CartAcceptanceTest extends DocumentConfiguration {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 추가 성공")
    void testSaveCartItem() {

        //given
        CartRequestDto cartRequestDto = new CartRequestDto(1L, 1L, 1, null);
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("saveCartItem-success"))
                .header("Authorization", "Bearer" + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .when()
                .post("/api/carts")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("장바구니 추가 실패")
    void testFailSaveCartItem() {

        //given
        CartRequestDto cartRequestDto = new CartRequestDto(1L, 999L, 1, null);
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("saveCartItem-Fail"))
                .header("Authorization", "Bearer" + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .when()
                .post("/api/carts")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("장바구니 조회")
    void testShowMemberCartItemList() {

        //given
        Member member = MEMBER;
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("showMemberCartItems-success"))
                .header("Authorization", "Bearer" + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/carts/")
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
        final String accessToken = 액세스_토큰_가져옴();
        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("removeCartItem-success"))
                .header("Authorization", "Bearer" + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("cartItemId", cartItemId)
                .when()
                .delete("/api/carts/{cartItemId}")
                .then()
                .log().all().extract();

        Optional<CartItem> cartItem = cartItemRepository.findById(1L);
        if (!cartItem.isPresent()) {
            System.out.println("삭제한 아이템 조회결과 : 없음! ");
        }

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                MEMBER_01.getCode(), MemberProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }
}