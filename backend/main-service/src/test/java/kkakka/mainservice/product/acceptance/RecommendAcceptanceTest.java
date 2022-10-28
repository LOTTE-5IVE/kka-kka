package kkakka.mainservice.product.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
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
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.product.ui.dto.ProductResponse;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RecommendAcceptanceTest extends DocumentConfiguration {

    private static final int MAXIMUM_PRODUCT_SIZE_WITH_PAGE_DEFAULT = 5 % 9;
    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    public void tearDown() {
        entityManager.unwrap(Session.class)
                .doWork(this::cleanUpTable);
    }

    private void cleanUpTable(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

        statement.executeUpdate("TRUNCATE TABLE \"order\"");
        statement.executeUpdate("TRUNCATE TABLE product_order");

        statement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @DisplayName("비회원일 때 상품 추천 - 성공")
    @Test
    void showRecommendAnonymous_success() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("show-recommend-anonymous-success"))
                .when()
                .get("/api/products/recommend")
                .then()
                .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("주문내역이 없는 회원일 때 상품 추천 - 성공")
    @Test
    void showRecommendMemberNoOrder_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("show-recommend-member-success-no-order"))
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/products/recommend")
                .then()
                .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getList("data", ProductResponse.class))
                .hasSize(MAXIMUM_PRODUCT_SIZE_WITH_PAGE_DEFAULT);
    }

    @DisplayName("주문내역이 있지만 후기는 없는 회원일 때 상품 추천 - 성공")
    @Test
    void showRecommendMemberNoReview_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();
        주문_요청함(accessToken, PRODUCT_1.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("show-recommend-member-success-no-review"))
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/products/recommend")
                .then()
                .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), MemberProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }

    private void 주문_요청함(String accessToken, Long productId) {
        OrderRequest orderRequest = new OrderRequest(
                List.of(new ProductOrderDto(productId, 1))
        );

        //when
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/api/orders")
                .then().log().all()
                .extract();
    }
}
