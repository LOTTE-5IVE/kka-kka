package kkakka.mainservice.coupon.acceptance;

import static kkakka.mainservice.fixture.TestAdminUser.TEST_ADMIN;
import static kkakka.mainservice.fixture.TestDataLoader.CATEGORY_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DiscountAcceptanceTest extends DocumentConfiguration {

    @DisplayName("상품 할인 생성 - 성공")
    @Test
    void createProductDiscount() {
        // given
        Product product = PRODUCT_1;
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-product-discount-success"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"productId\": " + product.getId() + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 10,\n"
                + "  \"discountType\": \"PRODUCT_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("카테고리 할인 생성 - 성공")
    @Test
    void createCategoryDiscount() {
        // given
        Category category = CATEGORY_1;
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-category-discount-success"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": " + category.getId() + ",\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 10,\n"
                + "  \"discountType\": \"CATEGORY_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("할인 생성 - 실패(유효하지않은 날짜)")
    @Test
    void createDiscountFailNotValidateDate() {
        // given
        Product product = PRODUCT_1;
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-discount-fail-invalid-date"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"productId\": " + product.getId() + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 10,\n"
                + "  \"discountType\": \"PRODUCT_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2020-01-02 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("할인 생성 - 실패(유효하지않은 할인율)")
    @Test
    void createDiscountNotValidateDiscount() {
        // given
        Product product = PRODUCT_1;
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-discount-fail-invalid-discount"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"productId\": " + product.getId() + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 200,\n"
                + "  \"discountType\": \"PRODUCT_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("할인 생성 - 실패(권한이 없는 경우)")
    @Test
    void createDiscount_unauthorized_fail(){
        // given
        Product product = PRODUCT_1;

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("create-product-discount-fail-unauthorized"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n"
                        + "  \"categoryId\": null,\n"
                        + "  \"productId\": " + product.getId() + ",\n"
                        + "  \"name\": \"test\",\n"
                        + "  \"discount\": 10,\n"
                        + "  \"discountType\": \"PRODUCT_DISCOUNT\",\n"
                        + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                        + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                        + "}")
                .when()
                .post("/api/coupons/discount")
                .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        Assertions.assertThat(response.header("Location")).isNull();
    }

    private String 카테고리_할인_생성() {
        Category category = CATEGORY_1;
        final String adminToken = 관리자_로그인();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " +adminToken)
            .body("{\n"
                + "  \"categoryId\": " + category.getId() + ",\n"
                + "  \"productId\": null,\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 10,\n"
                + "  \"discountType\": \"CATEGORY_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();
        return response.header("Location");
    }

    private String 상품_할인_생성() {
        final String adminToken = 관리자_로그인();

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + adminToken)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"productId\": " + PRODUCT_1.getId() + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"discount\": 10,\n"
                + "  \"discountType\": \"PRODUCT_DISCOUNT\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\"\n"
                + "}")
            .when()
            .post("/api/coupons/discount")
            .then().log().all().extract();
        return response.header("Location");
    }

    @DisplayName("상품 할인 삭제 - 성공")
    @Test
    void deleteProductDiscount() {
        // given
        String discountId = 상품_할인_생성();
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-product-discount-success"))
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .put("/api/coupons/discount/" + discountId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("카테고리 할인 삭제 - 성공")
    @Test
    void deleteCategoryDiscount() {
        // given
        String discountId = 카테고리_할인_생성();
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-category-discount-success"))
            .header("Authorization", "Bearer" + adminToken)
            .when()
            .put("/api/coupons/discount/" + discountId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("할인 조회 - 성공")
    @Test
    void showAllDiscounts() {
        // given
        상품_할인_생성();
        final String adminToken = 관리자_로그인();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-discounts-success"))
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/api/coupons/discount")
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


    private String 관리자_로그인() {
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
}
