package kkakka.mainservice.cart.ui;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

class CartAcceptanceTest extends DocumentConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    public void tearDown() {
        entityManager.unwrap(Session.class)
                .doWork(this::cleanUpTable);
    }

    private void cleanUpTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

        statement.executeUpdate("TRUNCATE TABLE cart_item");
        statement.executeUpdate("TRUNCATE TABLE cart");

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    @DisplayName("장바구니 추가 - 성공")
    void addCartItem_success() {
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
    @DisplayName("장바구니 추가 - 실패")
    void addCartItem_fail() {
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
    @DisplayName("장바구니 조회 - 성공")
    void showCart_success() {
        //given
        final String accessToken = 액세스_토큰_가져옴();
        장바구니_추가함(accessToken, PRODUCT_1.getId(), 1);
        장바구니_추가함(accessToken, PRODUCT_2.getId(), 1);
        final CartResponseDto cart = 장바구니에서_찾아옴(accessToken);
        퍼센트_쿠폰_생성();
        장바구니_쿠폰_적용(cart.getCartItemDtos().get(0).getId(),1L,accessToken);

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
        assertThat(response.body().jsonPath().getList("cartItems", CartItemDto.class)).hasSize(2);
    }

    @Test
    @DisplayName("장바구니의 아이템 수량 변경 - 성공")
    void changeCartItem_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();
        장바구니_추가함(accessToken, PRODUCT_1.getId(), 1);
        장바구니_추가함(accessToken, PRODUCT_2.getId(), 1);

        final CartRequestDto cartRequestDto = new CartRequestDto(PRODUCT_1.getId(), 3);

        // when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("cart-item-change-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .when()
                .post("/api/carts")
                .then()
                .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        final CartResponseDto cartResponseDto = 장바구니에서_찾아옴(accessToken);
        assertThat(
                cartResponseDto.getCartItemDtos().stream()
                        .filter(cartItemDto -> cartItemDto.getProductId().equals(PRODUCT_1.getId()))
                        .findAny().orElseThrow()
                        .getQuantity()
        ).isEqualTo(3);
    }

    @Test
    @DisplayName("장바구니 아이템 삭제")
    void removeCartItem_success() {
        //given
        final String accessToken = 액세스_토큰_가져옴();
        장바구니_추가함(accessToken, PRODUCT_1.getId(), 1);
        장바구니_추가함(accessToken, PRODUCT_2.getId(), 1);
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

    @Test
    @DisplayName("장바구니 쿠폰 적용 - 성공")
    void applyCouponCartItem_success() {
        //given
        final String accessToken = 액세스_토큰_가져옴();
        장바구니_추가함(accessToken, PRODUCT_1.getId(), 1);
        final CartResponseDto cart = 장바구니에서_찾아옴(accessToken);
        퍼센트_쿠폰_생성();
        String couponId = 사용가능_쿠폰_조회(PRODUCT_1.getId());

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("applyCartItemCoupon-success"))
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/carts/" + cart.getCartItemDtos().get(0).getId() + "/" + couponId)
                .then().log().all().extract();

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

    private void 장바구니_추가함(String accessToken, long productId, int quantity) {
        RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequestDto(productId, quantity))
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

    private void 퍼센트_쿠폰_생성() {
        RestAssured.given(spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n"
                        + "  \"categoryId\": null,\n"
                        + "  \"grade\": null,\n"
                        + "  \"productId\": " + PRODUCT_1.getId() + ",\n"
                        + "  \"name\": \"test\",\n"
                        + "  \"priceRule\": \"COUPON\",\n"
                        + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                        + "  \"expiredAt\": \"2025-01-01 00:00:00\",\n"
                        + "  \"percentage\": 10,\n"
                        + "  \"maxDiscount\": 2000,\n"
                        + "  \"minOrderPrice\": 20000\n"
                        + "}")
                .when()
                .post("/api/coupons")
                .then().log().all().extract();
    }

    private String 사용가능_쿠폰_조회(Long productId) {

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/coupons/" + productId)
                .then().log().all().extract();
        return response.body().path("[0].id").toString();
    }

    private void 장바구니_쿠폰_적용(Long cartItemId, Long couponId, String accessToken) {
        RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/carts/" + cartItemId + "/" + couponId)
                .then().log().all();
    }
}