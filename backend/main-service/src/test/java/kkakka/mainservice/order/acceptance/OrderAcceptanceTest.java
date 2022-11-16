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

        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), null, 2);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(PRODUCT_2.getId(), null, 1);
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

    @DisplayName("쿠폰 적용 상품 주문 - 성공")
    @Test
    public void orderWithCoupon() {
        // given
        tearDown();
        String accessToken = 액세스_토큰_가져옴();
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 20, 2000, 200);
        상품_쿠폰_다운로드(accessToken, couponId);
        ProductOrderDto productOrderDto = new ProductOrderDto(PRODUCT_1.getId(),
            Long.parseLong(couponId), 3);
        List<ProductOrderDto> productOrderDtoList = new ArrayList<>();
        productOrderDtoList.add(productOrderDto);
        OrderRequest orderRequest = new OrderRequest(
            new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()), productOrderDtoList);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("order-with-coupon"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequest)
            .when()
            .post("/api/orders/")
            .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("쿠폰 적용 상품 주문 - 실패(최소주문금액 부족)")
    @Test
    void orderUnderMinOrderPrice_fail() {
        // given
        String accessToken = 액세스_토큰_가져옴();
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 20, 2000, 20000);
        상품_쿠폰_다운로드(accessToken, couponId);
        ProductOrderDto productOrderDto = new ProductOrderDto(PRODUCT_1.getId(),
            Long.parseLong(couponId), 3);
        List<ProductOrderDto> productOrderDtoList = new ArrayList<>();
        productOrderDtoList.add(productOrderDto);
        OrderRequest orderRequest = new OrderRequest(
            new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()), productOrderDtoList);

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequest)
            .when()
            .post("/api/orders")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("주문내역 조회 - 성공")
    @Test
    void findMemberOrders_success() {
        tearDown();

        //given
        String accessToken = 액세스_토큰_가져옴();
        주문_요청함(accessToken, PRODUCT_1.getId(), null);
        후기_작성함(accessToken, "test-review", 5.0);
        주문_요청함(accessToken, PRODUCT_2.getId(), null);

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
        tearDown();

        //given
        String accessToken = 액세스_토큰_가져옴();
        주문_요청함(accessToken, PRODUCT_1.getId(), null);
        주문_요청함(accessToken, PRODUCT_1.getId(), null);
        주문_요청함(accessToken, PRODUCT_1.getId(), null);
        주문_요청함(accessToken, PRODUCT_1.getId(), null);
        주문_요청함(accessToken, PRODUCT_1.getId(), null);

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

    @DisplayName("총 주문 수 조회 - 성공")
    @Test
    void findMemberOrderCount_success() {
        tearDown();

        //given
        String accessToken = 액세스_토큰_가져옴();
        Long order1 = 주문_요청함(accessToken, PRODUCT_1.getId(), null);
        Long order2 = 주문_요청함(accessToken, PRODUCT_1.getId(), null);
        Long order3 = 주문_요청함(accessToken, PRODUCT_1.getId(), null);
        Long order4 = 주문_요청함(accessToken, PRODUCT_1.getId(), null);
        Long order5 = 주문_요청함(accessToken, PRODUCT_1.getId(), null);
        int count = List.of(order1, order2, order3, order4, order5).size();

        //when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("orders-info-member-count-success"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/members/me/orders/all")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().path("orderCount").toString()).isEqualTo(String.valueOf(count));
    }

    @DisplayName("쿠폰 적용 상품 주문 조회 - 성공")
    @Test
    public void findMemberOrdersWithCoupon_success() {
        tearDown();
        // given
        String accessToken = 액세스_토큰_가져옴();
        String couponId = 쿠폰_생성함(null, PRODUCT_1.getId(), "COUPON", 20, 2000, 200);
        상품_쿠폰_다운로드(accessToken, couponId);
        주문_요청함(accessToken, PRODUCT_1.getId(), Long.parseLong(couponId));

        // when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .header("Authorization", "Bearer " + accessToken)
            .filter(document("product-order-with-coupon"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/api/members/me/orders")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String 쿠폰_생성함(String grade, Long productId, String priceRule, Integer percentage,
        Integer maxDiscount, Integer minOrderPrice) {
        if (grade != null) {
            grade = "\"" + grade + "\"";
        }
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("{\n"
                + "  \"categoryId\": null,\n"
                + "  \"grade\": " + grade + ",\n"
                + "  \"productId\": " + productId + ",\n"
                + "  \"name\": \"test\",\n"
                + "  \"priceRule\": \"" + priceRule + "\",\n"
                + "  \"startedAt\": \"2020-01-01 00:00:00\",\n"
                + "  \"expiredAt\": \"2025-01-01 00:00:00\",\n"
                + "  \"percentage\": " + percentage + ",\n"
                + "  \"maxDiscount\": " + maxDiscount + ",\n"
                + "  \"minOrderPrice\": " + minOrderPrice + "\n"
                + "}")
            .when()
            .post("/api/coupons")
            .then().log().all().extract();
        return response.header("Location");
    }

    private String 상품_쿠폰_다운로드(String accessToken, String couponId) {

        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/coupons/download/" + couponId)
            .then().log().all().extract();

        return couponId;
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

    private Long 주문_요청함(String accessToken, Long productId, Long couponId) {
        OrderRequest orderRequest = new OrderRequest(
                new RecipientRequest(TEST_MEMBER_01.getName(), TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(), TEST_MEMBER_01.getAddress()),
                List.of(new ProductOrderDto(productId, couponId, 1))
        );

        //when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/api/orders")
                .then().log().all()
                .extract();

        return Long.valueOf(response.header("Location"));
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
