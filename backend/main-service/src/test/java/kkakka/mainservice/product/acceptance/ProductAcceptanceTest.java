package kkakka.mainservice.product.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.CATEGORY_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.fixture.TestDataLoader;
import kkakka.mainservice.product.domain.Product;
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

    @Test
    @DisplayName("전체 상품 조회(카테고리 id = 0) - 성공")
    void showAllProductsCategory_success() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("products-show-all-category0-success"))
                .when()
                .get("/api/products?category=0")
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("전체 상품 조회(2페이지 이상) - 성공")
    void showAllProductsPage_success() {
        //given
        int pageSize = 3;
        //when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("products-show-all-page-success"))
                .when()
                .get("/api/products?page=1&size=" + pageSize)
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().get("pageInfo.lastPage").toString())
                .isEqualTo(String.valueOf(calculatePage(TestDataLoader.ALL_PRODUCTS, pageSize)));
    }

    private int calculatePage(List<Product> products, int pageSize) {
        if (products.size() % pageSize > 0) {
            return products.size() / pageSize + 1;
        }
        return products.size() / pageSize;
    }
}
