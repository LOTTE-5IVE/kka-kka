package kkakka.mainservice.coupon.acceptance;

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
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.RecipientRequest;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CouponAcceptanceTest extends DocumentConfiguration {

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

        statement.executeUpdate("TRUNCATE TABLE coupon");
        statement.executeUpdate("TRUNCATE TABLE member_coupon");

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @DisplayName("???????????? ?????? - ??????")
    @Test
    void createGradeCoupon() {
        // given
        final String adminToken = ?????????_?????????();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-grade-coupon"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": \"GOLD\",\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"priceRule\": \"GRADE_COUPON\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\",\n"
                + "  \"percentage\": 10,\n"
                + "  \"maxDiscount\": 2000,\n"
                + "  \"minOrderPrice\": 20000\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("?????? ?????? ?????? - ??????")
    @Test
    void createCoupon() {
        // given
        final String adminToken = ?????????_?????????();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-coupon"))
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
                + "  \"minOrderPrice\": 20000\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("?????? ?????? - ??????(????????? ?????? ??????)")
    @Test
    void createCoupon_fail() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("create-coupon-fail-unauthorized"))
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

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        Assertions.assertThat(response.header("Location")).isNull();
    }

    @DisplayName("?????? ?????? - ??????")
    @Test
    void findAllCoupons() {
        tearDown();
        ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", null, 1000);
        final String adminToken = ?????????_?????????();

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("find-all-coupons"))
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/coupons")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("???????????? ?????? - ??????")
    @Test
    void deleteCouponByAdmin() {
        tearDown();
        final String adminToken = ?????????_?????????();
        String couponId = ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 12, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-coupon"))
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .put("/api/coupons/" + couponId)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("???????????? ?????? ?????? - ??????")
    @Test
    void deleteMemberCouponByAdmin() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String coupon = ??????_??????_????????????(accessToken);

        final String adminToken = ?????????_?????????();
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-downloaded-coupon"))
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .put("/api/coupons/" + coupon)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("?????? ????????? ?????? ?????? - ??????")
    @Test
    void downloadableCoupons() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 15, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("find-downloadable-coupons"))
            .when()
            .get("/api/coupons/download")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("????????? ?????? ????????? ?????? ?????? - ??????")
    @Test
    void showUsableCoupons() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        ??????_??????_????????????(accessToken);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("find-usable-coupons"))
            .when()
            .get("/api/coupons/me/available")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("????????? ????????? ?????? ?????? - ??????")
    @Test
    void showUsedCoupons() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String couponId = ??????_??????_????????????(accessToken);
        ??????_?????????(accessToken, PRODUCT_1.getId(), Long.parseLong(couponId));

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("find-used-coupons"))
            .when()
            .get("/api/coupons/me/unavailable")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    private String ??????_?????????(Long categoryId, String grade, Long productId, String priceRule,
        Integer percentage,
        Integer maxDiscount) {
        if (grade != null) {
            grade = "\"" + grade + "\"";
        }
        final String adminToken = ?????????_?????????();
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": " + grade + ",\n"
                + "  \"productId\": " + productId + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"priceRule\": \"" + priceRule + "\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\",\n"
                + "  \"percentage\": " + percentage + ",\n"
                + "  \"maxDiscount\": " + maxDiscount + ",\n"
                + "  \"minOrderPrice\": 200\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();
        return response.header("Location");
    }

    @DisplayName("?????? ????????????")
    @Test
    void downloadCoupon() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String couponId = ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 20, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("download-coupon"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

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

    private String ??????_??????_????????????(String accessToken) {
        String couponId = ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 20, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
    }

    private String ??????_??????_????????????(String accessToken) {
        String couponId = ??????_?????????(null, "BRONZE", null, "GRADE_COUPON", 50, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
    }

    @DisplayName("?????? - ?????? ??????????????? ?????? ??????")
    @Test
    void showProductCouponsByProductIdAndMemberId() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        ??????_??????_????????????(accessToken);
        ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 10, 200);
        String couponId = ??????_??????_????????????(accessToken);
        ??????_?????????(accessToken, PRODUCT_1.getId(), Long.parseLong(couponId));

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-product-coupon-by-productId-and-memberId"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get("/api/coupons/me/products/" + PRODUCT_1.getId())
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("????????? - ?????? ??????????????? ?????? ??????")
    @Test
    void showProductCouponsByProductId() {
        tearDown();
        String accessToken = ?????????_??????_?????????();
        ??????_??????_????????????(accessToken);
        ??????_??????_????????????(accessToken);
        ??????_?????????(PRODUCT_1.getCategory().getId(), null, null, "COUPON", 14, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-product-coupon-by-productId"))
            .when()
            .get("/api/coupons/products/" + PRODUCT_1.getId())
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("???????????? ?????? ?????? ?????? - ??????")
    @Test
    void createMoneyCoupon() {
        // given
        tearDown();
        final String adminToken = ?????????_?????????();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
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
                + "  \"percentage\": null,\n"
                + "  \"maxDiscount\": 2000,\n"
                + "  \"minOrderPrice\": 20000\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("??? ?????? ??? ?????? - ??????")
    @Test
    void findMemberCouponCount_success() {
        // given
        tearDown();
        String accessToken = ?????????_??????_?????????();
        ??????_??????_????????????(accessToken);
        ??????_??????_????????????(accessToken);
        ??????_?????????(null, null, PRODUCT_2.getId(), "COUPON", 14, 2000);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-member-coupons-success"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get("/api/members/me/coupons/all")
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("?????? ?????? ???????????? - ??????")
    @Test
    void downloadProductCoupon_success() {
        // given
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String couponId = ??????_?????????(null, null, PRODUCT_1.getId(), "COUPON", 20, 2000);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("download-product-coupon"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/" + PRODUCT_1.getId() + "/" + couponId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("?????? ???????????? ?????? ?????? - ??????")
    @Test
    void applyProductCoupon_success() {
        // given
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String couponId = ??????_??????_????????????(accessToken);
        ProductOrderDto productOrderDto = new ProductOrderDto(PRODUCT_1.getId(), null, 3);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productOrderDto)
            .when()
            .post("/api/orders/" + couponId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("?????? ???????????? ?????? ?????? ?????? - ??????")
    @Test
    void cancelProductCoupon_success() {
        // given
        tearDown();
        String accessToken = ?????????_??????_?????????();
        String couponId = ????????????_??????_??????(accessToken);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("orders-products-cancel-coupon"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .put("/api/orders/" + couponId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String ????????????_??????_??????(String accessToken) {
        String couponId = ??????_??????_????????????(accessToken);
        ProductOrderDto productOrderDto = new ProductOrderDto(PRODUCT_1.getId(), null, 3);

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productOrderDto)
            .when()
            .post("/api/orders/" + couponId)
            .then().log().all().extract();

        return couponId;
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

    private void ??????_?????????(String accessToken, Long productId, Long couponId) {
        OrderRequest orderRequest = new OrderRequest(
            new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()),
            List.of(new ProductOrderDto(productId, couponId, 1))
        );

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequest)
            .when()
            .post("/api/orders")
            .then().log().all()
            .extract();
    }
}