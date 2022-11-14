package kkakka.mainservice.review.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_ORDER_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_ORDER_2;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_ORDER_3;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_ORDER_4;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_ORDER_5;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReviewAcceptanceTest extends DocumentConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    public void tearDown() {
        entityManager.unwrap(Session.class)
                .doWork(this::cleanUpTable);
    }

    private void cleanUpTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("TRUNCATE TABLE Review");
    }

    @DisplayName("상품후기 작성 - 성공")
    @Test
    void writeReview_success() {
        tearDown();

        // given
        final String accessToken = 액세스_토큰_가져옴();
        final ProductOrder productOrder = PRODUCT_ORDER_1;
        final ReviewRequest reviewRequest = new ReviewRequest("review test", 5.0);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-write"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + productOrder.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("상품후기 작성 - 실패(권한없음)")
    @Test
    void writeReview_fail() {
        tearDown();

        // given
        final ProductOrder productOrder = PRODUCT_ORDER_1;
        final ReviewRequest reviewRequest = new ReviewRequest("fail review", 3.5);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-write-fail"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + productOrder.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("상품후기 작성 - 실패(이미 작성함")
    @Test
    void writeReview_fail_already_written() {
        tearDown();

        // given
        final String accessToken = 액세스_토큰_가져옴();
        후기_작성함(accessToken, "review_01", PRODUCT_ORDER_1);

        final ReviewRequest reviewRequest = new ReviewRequest("review product_1", 5.0);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-write-fail-already-written"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + PRODUCT_ORDER_1.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품에 대한 후기 조회 - 성공")
    @Test
    void showReviews_success() {
        tearDown();

        // given
        final String accessToken = 액세스_토큰_가져옴(TEST_MEMBER_01.getCode());
        후기_작성함(accessToken, "review_01", PRODUCT_ORDER_1);
        후기_작성함(accessToken, "review_02", PRODUCT_ORDER_2);
        후기_작성함(accessToken, "review_03", PRODUCT_ORDER_3);
        후기_작성함(accessToken, "review_04", PRODUCT_ORDER_4);
        후기_작성함(accessToken, "review_05", PRODUCT_ORDER_5);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-show"))
                .when()
                .get("/api/reviews?product=" + PRODUCT_1.getId() + "&page=1&size=6")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("총 상품 후기 수 조회 - 성공")
    @Test
    void showAllReviewByProduct_success(){
        tearDown();

        // given
        final String accessToken = 액세스_토큰_가져옴(TEST_MEMBER_01.getCode());
        final Long review_01 = 후기_작성함(accessToken, "review_01", PRODUCT_ORDER_1);
        final Long review_02 = 후기_작성함(accessToken, "review_02", PRODUCT_ORDER_2);
        final Long review_03 = 후기_작성함(accessToken, "review_03", PRODUCT_ORDER_3);
        final Long review_04 = 후기_작성함(accessToken, "review_04", PRODUCT_ORDER_4);
        final Long review_05 = 후기_작성함(accessToken, "review_05", PRODUCT_ORDER_5);

        int count = List.of(review_01, review_02, review_03, review_04, review_05).size();

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("review-count-success"))
                .when()
                .get("/api/reviews/" + PRODUCT_ORDER_1.getProduct().getId() + "/all")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().path("reviewCount").toString()).isEqualTo(String.valueOf(count));
    }

    private Long 후기_작성함(String accessToken, String contents, ProductOrder productOrder) {
        final ReviewRequest reviewRequest = new ReviewRequest(contents, 5.0);

        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + productOrder.getId())
                .then().log().all()
                .extract();

        return Long.valueOf(response.header("Location"));
    }

    private String 액세스_토큰_가져옴(String code) {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                code, ProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), ProviderName.TEST);

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
