package kkakka.mainservice.coupon.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.member.domain.Grade;
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

    @DisplayName("등급쿠폰 생성 - 멤버 없을 시 실패")
    @Test
    void createGradeCoupon() {
        CouponRequestDto couponRequestDto = new CouponRequestDto(
            null, Grade.GOLD, null, "test", "test-detail",
            PriceRule.GRADE_COUPON.name(),
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0),
            10, 2000, 20000
        );

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": \"GOLD\",\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"detail\": \"test-detail\",\n"
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

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 쿠폰 생성 - 성공")
    @Test
    void createCoupon() {
        CouponRequestDto couponRequestDto = new CouponRequestDto(
            null, null, null, "쿠폰", "쿠폰설명",
            PriceRule.COUPON.name(),
            LocalDateTime.of(2020, 1, 1, 9, 0),
            LocalDateTime.of(2025, 1, 1, 9, 0),
            10, 2000, 20000
        );

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": null,\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"detail\": \"test-detail\",\n"
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

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("쿠폰 조회 - 성공")
    @Test
    void findAllCoupons() {
        일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .get("/api/coupons")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("쿠폰 사용(삭제) - 성공")
    @Test
    void useCouponByAdmin() {
        String coupon = 일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .put("/api/coupons/" + coupon)
            .then().log().all().extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("다운 가능한 쿠폰 조회 - 성공")
    @Test
    void downloadableCoupons() {
        일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .get("/api/coupons/download")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("사용 가능한 쿠폰 조회 - 성공")
    @Test
    void usableCoupons() {
        String couponId = 일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .get("/api/coupons/me")
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String 일반_쿠폰_생성함() {
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": null,\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"detail\": \"test-detail\",\n"
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
        String couponId = 일반_쿠폰_생성함();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when()
            .get("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }
}