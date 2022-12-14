package kkakka.mainservice.cart.acceptance;

import static kkakka.mainservice.fixture.TestAdminUser.TEST_ADMIN;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.cart.ui.dto.CartItemDto;
import kkakka.mainservice.cart.ui.dto.CartRequestDto;
import kkakka.mainservice.cart.ui.dto.CartResponseDto;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
    @DisplayName("???????????? ?????? - ??????")
    void addCartItem_success() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto(PRODUCT_1.getId(), 1);
        final String accessToken = ?????????_??????_?????????();

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
    @DisplayName("???????????? ?????? - ??????")
    void addCartItem_fail() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto(999L, 1);
        final String accessToken = ?????????_??????_?????????();

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
    @DisplayName("???????????? ?????? - ??????")
    void showCart_success() {
        //given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        ????????????_?????????(accessToken, PRODUCT_2.getId(), 1);
        final CartResponseDto cart = ??????????????????_?????????(accessToken);
        String couponId = ?????????_??????_??????(200);
        ????????????_??????_??????(cart.getCartItemDtos().get(0).getCartItemId(), couponId, accessToken);

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
    @DisplayName("??????????????? ????????? ?????? ?????? - ??????")
    void changeCartItem_success() {
        // given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        ????????????_?????????(accessToken, PRODUCT_2.getId(), 1);

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

        final CartResponseDto cartResponseDto = ??????????????????_?????????(accessToken);
        assertThat(
                cartResponseDto.getCartItemDtos().stream()
                        .filter(cartItemDto -> cartItemDto.getId().equals(PRODUCT_1.getId()))
                        .findAny().orElseThrow()
                        .getQuantity()
        ).isEqualTo(3);
    }

    @Test
    @DisplayName("???????????? ????????? ??????")
    void removeCartItem_success() {
        //given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        ????????????_?????????(accessToken, PRODUCT_2.getId(), 1);
        final CartResponseDto cart = ??????????????????_?????????(accessToken);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("removeCartItem-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/carts/" + cart.getCartItemDtos().get(0).getCartItemId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ??????")
    void applyCouponCartItem_success() {
        //given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        final CartResponseDto cart = ??????????????????_?????????(accessToken);
        String couponId = ?????????_??????_??????(200);
        ??????_??????_????????????(accessToken, couponId);

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("applyCartItemCoupon-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/carts/" + cart.getCartItemDtos().get(0).getCartItemId() + "/"
                        + couponId)
                .then().log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ??????(?????????????????? ??????)")
    void applyCouponCartItem_fail() {
        //given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        final CartResponseDto cart = ??????????????????_?????????(accessToken);
        String couponId = ?????????_??????_??????(20000);
        ??????_??????_????????????(accessToken, couponId);

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
            .filter(document("applyCartItemCoupon-fail"))
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/carts/" + cart.getCartItemDtos().get(0).getCartItemId() + "/"
                + couponId)
            .then().log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("???????????? ?????? ?????? ?????? - ??????")
    @Test
    void testCancelCouponCartItem_success() {
        //given
        final String accessToken = ?????????_??????_?????????();
        ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        ????????????_?????????(accessToken, PRODUCT_2.getId(), 1);
        final CartResponseDto cart = ??????????????????_?????????(accessToken);
        String couponId = ?????????_??????_??????(200);
        ??????_??????_????????????(accessToken, couponId);
        ????????????_??????_??????(cart.getCartItemDtos().get(0).getCartItemId(), couponId, accessToken);

        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("cancelCartItemCoupon-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/carts/" + cart.getCartItemDtos().get(0).getCartItemId() + "/"
                        + couponId)
                .then().log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("??? ???????????? ????????? ??? ?????? - ??????")
    @Test
    void findMemberCartItemCount_success() {
        // given
        final String accessToken = ?????????_??????_?????????();
        final Long cart1 = ????????????_?????????(accessToken, PRODUCT_1.getId(), 1);
        final Long cart2 = ????????????_?????????(accessToken, PRODUCT_2.getId(), 1);
        int count = List.of(cart1, cart2).size();

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("cart-item-count-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/members/me/carts/all")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().path("cartCount").toString()).isEqualTo(String.valueOf(count));
    }

    private CartResponseDto ??????????????????_?????????(String accessToken) {
        final ExtractableResponse<Response> response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/carts")
                .then().log().all().extract();

        return response.body().as(CartResponseDto.class);
    }

    private Long ????????????_?????????(String accessToken, long productId, int quantity) {
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequestDto(productId, quantity))
                .when()
                .post("/api/carts")
                .then().log().all()
                .extract();

        return Long.valueOf(response.header("Location"));
    }

    private String ?????????_??????_?????????() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), ProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }

    private String ?????????_?????????() {
        Map<String, String> request = new HashMap<>();
        request.put("userId", TEST_ADMIN.getUserId());
        request.put("password", TEST_ADMIN.getPassword());

        final ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/api/admin/login")
            .then().extract();
        return response.body().jsonPath().get("adminToken");
    }

    private String ?????????_??????_??????(Integer minOrderPrice) {
        final String adminToken = ?????????_?????????();

        final ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + adminToken)
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
                        + "  \"minOrderPrice\": " + minOrderPrice + "\n"
                        + "}")
                .when()
                .post("/api/coupons")
                .then().log().all().extract();
        return response.header("Location");
    }

    private void ????????????_??????_??????(Long cartItemId, String couponId, String accessToken) {
        RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/carts/" + cartItemId + "/" + couponId)
                .then().log().all();
    }

    private String ??????_??????_????????????(String accessToken, String couponId) {
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/coupons/download/" + couponId)
                .then().log().all().extract();

        return couponId;
    }
}