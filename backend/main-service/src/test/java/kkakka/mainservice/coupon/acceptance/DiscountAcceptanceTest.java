package kkakka.mainservice.coupon.acceptance;

import static kkakka.mainservice.cart.TestDataLoader.CATEGORY;
import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-product-discount-success"))
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
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.header("Location")).isNotNull();
    }

    @DisplayName("카테고리 할인 생성 - 성공")
    @Test
    void createCategoryDiscount() {
        // given
        Category category = CATEGORY;

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-category-discount-success"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-discount-fail-invalid-date"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    @DisplayName("할인 생성 - 실패(유효하지않은 날짜)")
    @Test
    void createDiscountNotValidateDiscount() {
        // given
        Product product = PRODUCT_1;

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("create-discount-fail-invalid-discount"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    private String 카테고리_할인_생성() {
        Category category = CATEGORY;

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-product-discount-success"))
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

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("delete-category-discount-success"))
            .when()
            .put("/api/coupons/discount/" + discountId)
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("할인 조회 - 성공")
    @Test
    public void showAllDiscounts() {
        // given
        상품_할인_생성();

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-discounts-success"))
            .when()
            .get("/api/coupons/discount")
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
