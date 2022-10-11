package kkakka.mainservice.review.acceptance;

import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReviewAcceptanceTest extends DocumentConfiguration {

    @DisplayName("상품후기 작성 - 성공")
    @Test
    void writeReview_success(){
        // given
        final String accessToken = 액세스_토큰_가져옴();
        final Product product = PRODUCT_1;
        final ReviewRequest reviewRequest = new ReviewRequest("review test");

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-write"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?product=" + product.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("상품후기 작성 - 실패(권한없음)")
    @Test
    void writeReview_fail(){
        // given
        final Product product = PRODUCT_1;
        final ReviewRequest reviewRequest = new ReviewRequest("fail review");

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-write-fail"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?product=" + product.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("특정 상품에 대한 후기 조회 - 성공")
    @Test
    void showReviews_success(){
        // given
        final String accessToken = 액세스_토큰_가져옴();
        후기_작성함(accessToken, "review_01", PRODUCT_1);
        후기_작성함(accessToken, "review_02", PRODUCT_1);
        후기_작성함(accessToken, "review_03", PRODUCT_1);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-show"))
                .when()
                .get("/api/reviews?product=" + PRODUCT_1.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 후기_작성함(String accessToken, String contents, Product product) {
        final ReviewRequest reviewRequest = new ReviewRequest(contents);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?product=" + product.getId())
                .then().log().all();
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                MEMBER_01.getCode(), MemberProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }
}
