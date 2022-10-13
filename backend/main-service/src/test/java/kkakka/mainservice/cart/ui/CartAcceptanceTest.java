package kkakka.mainservice.cart.ui;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CartAcceptanceTest extends DocumentConfiguration {

    @Test
    @DisplayName("장바구니 추가 성공")
    void testSaveCartItem() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto(PRODUCT_1.getId(), 1);
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("saveCartItem-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .when()
                .post("/api/carts")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    @Test
    @DisplayName("장바구니 추가 실패")
    void testFailSaveCartItem() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto(999L, 1);
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("saveCartItem-Fail"))
                .header("Authorization", "Bearer " + accessToken)
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
        final String accessToken = 액세스_토큰_가져옴();

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("showMemberCartItems-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/carts")
                .then()
                .log().all().extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니 아이템 삭제")
    void testRemoveCartItem() {
        //given
        final String accessToken = 액세스_토큰_가져옴();
        장바구니_추가함(accessToken, PRODUCT_1.getId());
        장바구니_추가함(accessToken, PRODUCT_2.getId());
        final CartResponseDto cart = 장바구니에서_찾아옴(accessToken);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("removeCartItem-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/carts/" + cart.getCartItemDtos().get(0).getId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private CartResponseDto 장바구니에서_찾아옴(String accessToken) {
        final ExtractableResponse<Response> response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/carts")
                .then().log().all().extract();

        return response.body().as(CartResponseDto.class);
    }

    private void 장바구니_추가함(String accessToken, long productId) {
        RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequestDto(productId, 1))
                .when()
                .post("/api/carts")
                .then().log().all();
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), MemberProviderName.TEST);

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