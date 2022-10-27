package kkakka.mainservice.product.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.CATEGORY_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.product.ui.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                .get("/api/products?category=" + CATEGORY_1.getId())
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

    @DisplayName("검색 조회 - 성공")
    @Test
    void showProductsBySearchTest_success() {
        //given
        //when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("products-show-search"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/products?keyword=제로 쿠키")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getList("data", ProductResponse.class)).hasSize(1);
        assertThat(
                response.body().jsonPath().getList("data", ProductResponse.class)
                        .stream()
                        .map(productResponse -> productResponse.getName().contains("제로"))
                        .findAny())
                .isNotEmpty();
        assertThat(
                response.body().jsonPath().getList("data", ProductResponse.class)
                        .stream()
                        .map(productResponse -> productResponse.getName().contains("쿠키"))
                        .findAny())
                .isNotEmpty();
    }
}
