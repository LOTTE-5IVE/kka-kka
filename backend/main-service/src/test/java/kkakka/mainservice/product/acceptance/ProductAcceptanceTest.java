package kkakka.mainservice.product.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.product.ui.dto.SearchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProductAcceptanceTest extends DocumentConfiguration {

    @DisplayName("검색 조회 - 성공")
    @Test
    void showProductsBySearchTest_success() {
        //given
        SearchRequest searchRequest = new SearchRequest("제로 쿠키");

        //when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("products-show-search"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(searchRequest)
            .when()
            .get("/api/products/search")
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
