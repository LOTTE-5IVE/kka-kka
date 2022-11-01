package kkakka.mainservice.order.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.RecipientRequest;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class OrderAcceptanceTest extends DocumentConfiguration {

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

    @DisplayName("상품 주문 - 성공")
    @Test
    void productOrderTest_success() {
        //given
        String accessToken = 액세스_토큰_가져옴();

        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), 2);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(PRODUCT_2.getId(), 1);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);
        productOrderDtos.add(productOrderDto2);
        OrderRequest orderRequest = new OrderRequest(
                new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()),
                productOrderDtos
        );

        //when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("product-order-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/api/orders")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("주문내역 조회 - 성공")
    @Test
    void findMemberOrders_success() {
        //given
        String accessToken = 액세스_토큰_가져옴();
        주문_요청함(accessToken, PRODUCT_1.getId());
        후기_작성함(accessToken, "test-review", 5.0);
        주문_요청함(accessToken, PRODUCT_2.getId());

        //when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("orders-info-member-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/members/me/orders")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data")).hasSize(2);
    }

    @DisplayName("주문 조회 마지막 페이지가 아닐 때 - 성공")
    @Test
    void findMemberOrdersWithPage_success() {
        //given
        String accessToken = 액세스_토큰_가져옴();
        주문_요청함(accessToken, PRODUCT_1.getId());
        주문_요청함(accessToken, PRODUCT_1.getId());
        주문_요청함(accessToken, PRODUCT_1.getId());
        주문_요청함(accessToken, PRODUCT_1.getId());
        주문_요청함(accessToken, PRODUCT_1.getId());

        String curSize = "4";

        //when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("orders-info-member-not-last-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/members/me/orders?pageSize=" + curSize)
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().path("pageInfo.curSize").toString())
                .isEqualTo(curSize);
        assertThat(response.body().path("pageInfo.lastPage").toString())
                .isEqualTo("false");
    }

    private void 후기_작성함(String accessToken, String contents, Double rating) {
        final ReviewRequest reviewRequest = new ReviewRequest(contents, rating);
        final ExtractableResponse<Response> orderResponse = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/members/me/orders")
                .then().log().all()
                .extract();
        final String productOrderId = orderResponse.body().path("data[0].productOrders[0].id")
                .toString();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(reviewRequest)
                .when()
                .post("/api/reviews?productOrder=" + productOrderId)
                .then().log().all();
    }

    private void 주문_요청함(String accessToken, Long productId) {
        OrderRequest orderRequest = new OrderRequest(
                new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()),
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
