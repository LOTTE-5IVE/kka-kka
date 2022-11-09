package kkakka.mainservice.coupon.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CouponAcceptanceTest extends DocumentConfiguration {

    @Autowired
    CouponRepository couponRepository;

    @DisplayName("등급쿠폰 생성 - 성공")
    @Test
    void createGradeCoupon() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-grade-coupon"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    @DisplayName("일반 쿠폰 생성 - 성공")
    @Test
    void createCoupon() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-coupon"))
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
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("쿠폰 조회 - 성공")
    @Test
    void findAllCoupons() {
        쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", null, 1000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("find-all-coupons"))
            .when()
            .get("/api/coupons")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("일반쿠폰 삭제 - 성공")
    @Test
    void deleteCouponByAdmin() {
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 12, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-coupon"))
            .when()
            .put("/api/coupons/" + couponId)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("다운받은 쿠폰 삭제 - 성공")
    @Test
    void deleteMemberCouponByAdmin() {
        String accessToken = 액세스_토큰_가져옴();
        String coupon = 상품_쿠폰_다운로드(accessToken);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-downloaded-coupon"))
            .when()
            .put("/api/coupons/" + coupon)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("다운 가능한 쿠폰 조회 - 성공")
    @Test
    void downloadableCoupons() {
        String accessToken = 액세스_토큰_가져옴();
        쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 15, 2000);

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

    @DisplayName("회원별 사용 가능한 쿠폰 조회 - 성공")
    @Test
    void usableCoupons() {
        String accessToken = 액세스_토큰_가져옴();
        상품_쿠폰_다운로드(accessToken);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("find-usable-coupons"))
            .when()
            .get("/api/coupons/me")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    private String 쿠폰_생성함(String grade, Long productId, String priceRule, Integer percentage,
        Integer maxDiscount) {
        if (grade != null) {
            grade = "\"" + grade + "\"";
        }
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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
                + "  \"minOrderPrice\": 20000\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();
        return response.header("Location");
    }

    @DisplayName("쿠폰 다운로드")
    @Test
    void downloadCoupon() {
        String accessToken = 액세스_토큰_가져옴();
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 20, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("download-coupon"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    private String 액세스_토큰_가져옴() {
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

    private String 상품_쿠폰_다운로드(String accessToken) {
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 20, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
    }

    private String 등급_쿠폰_다운로드(String accessToken) {
        String couponId = 쿠폰_생성함("GOLD", null, "GRADE_COUPON", 50, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
    }

    @DisplayName("회원 - 상품 사용가능한 쿠폰 조회")
    @Test
    void showProductCouponsByProductIdAndMemberId() {
        String accessToken = 액세스_토큰_가져옴();
        상품_쿠폰_다운로드(accessToken);
        등급_쿠폰_다운로드(accessToken);
        쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 14, 2000);

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

    @DisplayName("비회원 - 상품 적용가능한 쿠폰 조회")
    @Test
    void showProductCouponsByProductId() {
        String accessToken = 액세스_토큰_가져옴();
        상품_쿠폰_다운로드(accessToken);
        등급_쿠폰_다운로드(accessToken);
        쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 14, 2000);

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-product-coupon-by-productId"))
            .when()
            .get("/api/coupons/products/" + PRODUCT_1.getId())
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("금액할인 일반 쿠폰 생성 - 성공")
    @Test
    void createMoneyCoupon() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    @DisplayName("총 쿠폰 수 확인 - 성공")
    @Test
    void findMemberCouponCount_success() {
        // given
        String accessToken = 액세스_토큰_가져옴();
        상품_쿠폰_다운로드(accessToken);
        등급_쿠폰_다운로드(accessToken);
        쿠폰_생성함(null, PRODUCT_2.getId(), "COUPON", 14, 2000);

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

    @DisplayName("상품 쿠폰 다운로드 - 성공")
    @Test
    void downloadProductCoupon_success() {
        // given
        String accessToken = 액세스_토큰_가져옴();
        String couponId = 상품_쿠폰_다운로드(accessToken);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("download-product-coupon"))
            .header("Authorization", "Bearer " + accessToken)
            .body("{\n"
                + "  \"id\": null,\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": null,\n"
                + "  \"productId\": " + PRODUCT_1.getId() + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"priceRule\": \"COUPON\",\n"
                + "  \"registerAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\",\n"
                + "  \"percentage\": 10,\n"
                + "  \"maxDiscount\": 2000,\n"
                + "  \"minOrderPrice\": 20000\n"
                + "  \"isDownloadable\": false\n"
                + "  \"discountedPrice\": null\n"
                + "}")
            .when()
            .post("/api/coupons/download/product/" + couponId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}