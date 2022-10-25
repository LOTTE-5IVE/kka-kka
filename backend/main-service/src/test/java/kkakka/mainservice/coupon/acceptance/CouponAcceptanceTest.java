package kkakka.mainservice.coupon.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.Grade;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
        일반_쿠폰_생성함();

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
        String coupon = 일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-coupon"))
            .when()
            .put("/api/coupons/" + coupon)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("다운받은 쿠폰 삭제 - 성공")
    @Test
    void deleteMemberCouponByAdmin() {
        String accessToken = 액세스_토큰_가져옴();
        String coupon = 쿠폰_다운로드(accessToken);

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
        일반_쿠폰_생성함();

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

    @DisplayName("사용 가능한 쿠폰 조회 - 성공")
    @Test
    void usableCoupons() {
        String accessToken = 액세스_토큰_가져옴();
        쿠폰_다운로드(accessToken);

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

    private String 일반_쿠폰_생성함() {
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
                + "  \"percentage\": 10,\n"
                + "  \"maxDiscount\": 2000,\n"
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
        String couponId = 일반_쿠폰_생성함();

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

    private String 쿠폰_다운로드(String accessToken) {
        String couponId = 일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
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
}