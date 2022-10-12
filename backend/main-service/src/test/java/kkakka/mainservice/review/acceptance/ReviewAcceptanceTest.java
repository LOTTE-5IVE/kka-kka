package kkakka.mainservice.review.acceptance;

import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.cart.TestDataLoader.PRODUCT_ORDER_1;
import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static kkakka.mainservice.fixture.TestMember.MEMBER_02;
import static kkakka.mainservice.fixture.TestMember.MEMBER_03;
import static kkakka.mainservice.fixture.TestMember.MEMBER_04;
import static kkakka.mainservice.fixture.TestMember.MEMBER_05;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
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
        // given
        final String accessToken = 액세스_토큰_가져옴();
        final ProductOrder productOrder = PRODUCT_ORDER_1;
        final ReviewRequest reviewRequest = new ReviewRequest("review test");

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
        // given
        final ProductOrder productOrder = PRODUCT_ORDER_1;
        final ReviewRequest reviewRequest = new ReviewRequest("fail review");

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
        // given
        final String accessToken = 액세스_토큰_가져옴();
        후기_작성함(accessToken, "review_01", PRODUCT_ORDER_1);

        final ReviewRequest reviewRequest = new ReviewRequest("review product_1");

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
        // given
        final String accessToken1 = 액세스_토큰_가져옴(MEMBER_01.getCode());
        final String accessToken2 = 액세스_토큰_가져옴(MEMBER_02.getCode());
        final String accessToken3 = 액세스_토큰_가져옴(MEMBER_03.getCode());
        final String accessToken4 = 액세스_토큰_가져옴(MEMBER_04.getCode());
        final String accessToken5 = 액세스_토큰_가져옴(MEMBER_05.getCode());
        후기_작성함(accessToken1, "review_01", PRODUCT_ORDER_1);
        후기_작성함(accessToken2, "review_02", PRODUCT_ORDER_1);
        후기_작성함(accessToken3, "review_03", PRODUCT_ORDER_1);
        후기_작성함(accessToken4, "review_04", PRODUCT_ORDER_1);
        후기_작성함(accessToken5, "review_05", PRODUCT_ORDER_1);

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

    private void 후기_작성함(String accessToken, String contents, ProductOrder productOrder) {
        final ReviewRequest reviewRequest = new ReviewRequest(contents);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + productOrder.getId())
                .then().log().all();
    }

    private String 액세스_토큰_가져옴(String code) {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                code, MemberProviderName.TEST);

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
