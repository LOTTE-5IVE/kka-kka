package kkakka.mainservice.elasticsearch.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.elasticsearch.application.dto.ProductDocumentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProductDocumentAcceptanceTest extends DocumentConfiguration {

    @DisplayName("검색 조회 - 성공")
    @Test
    void showProductsBySearch_success() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-products-search-success"))
            .when()
            .get("/api/products?keyword=웨하스")
            .then()
            .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getList("data", ProductDocumentDto.class)).hasSize(2);
        assertThat(response.body().jsonPath().getList("data",ProductDocumentDto.class).stream()
                            .map(productDocumentDto -> productDocumentDto.getName().contains("웨하스"))
                            .findAny())
            .isNotEmpty();
    }

    @DisplayName("자동 완성 조회 - 성공")
    @Test
    void showAutoCompleteByKeyword_success() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("show-product-names-search-autocomplete"))
            .when()
            .get("/api/es/auto?keyword=롯데")
            .then()
            .log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getList("autoKeyword",String.class).size()).isLessThanOrEqualTo(10);
        assertThat(response.body().jsonPath().getList("autoKeyword",String.class).stream()
            .map(name -> name.contains("롯데")))
            .isNotEmpty();
    }
}
