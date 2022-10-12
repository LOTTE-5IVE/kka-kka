package kkakka.mainservice.product.acceptance;

import static kkakka.mainservice.cart.TestDataLoader.CATEGORY;
import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProductAcceptanceTest extends DocumentConfiguration {

    @Test
    @DisplayName("상품 상세 조회 성공")
    void testShowDetailProduct() {
        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("showDetailProduct-success"))
                .when()
                .get("/api/products/" + PRODUCT_1.getId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품 상세 조회 실패")
    void testShowDetailProductFail() {
        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("showDetailProduct-fail"))
                .when()
                .get("/api/products/" + 999)
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("카테고리 상품 조회 성공")
    void testShowCategoryProducts() {
        //when
        ExtractableResponse<Response> response = RestAssured.given(spec).log().all()
                .filter(document("showCategoryProducts-success"))
                .when()
                .get("/api/products?category=" + CATEGORY.getId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("전체 상품 조회 - 성공")
    void showAllProducts_success() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("products-show-all-success"))
                .when()
                .get("/api/products")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
