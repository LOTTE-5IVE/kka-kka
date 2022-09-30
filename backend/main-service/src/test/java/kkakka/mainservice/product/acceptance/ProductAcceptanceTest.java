package kkakka.mainservice.product.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.cart.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kkakka.mainservice.cart.TestDataLoader.CATEGORY;
import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("상품 상세 조회")
    void testShowDetailProduct() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/products/" + PRODUCT_1.getId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("카테고리 상품 조회")
    void testShowCategoryProducts() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products/products?category=" + CATEGORY.getId())
                .then()
                .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
