package kkakka.mainservice.order.acceptance;

import static kkakka.mainservice.fixture.TestMember.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class OrderAcceptanceTest extends DocumentConfiguration {

    @Autowired
    ProductRepository productRepository;

    @DisplayName("상품 주문 - 성공")
    @Test
    void productOrderTest_success() {
        //given
        String accessToken = 액세스_토큰_가져옴();

        ProductOrderDto productOrderDto1 = new ProductOrderDto(1L,2);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(2L,1);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);
        productOrderDtos.add(productOrderDto2);
        OrderRequest orderRequest = new OrderRequest(productOrderDtos);

        //when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("product-order-success"))
            .header("Authorization","Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequest)
            .when()
            .post("api/orders")
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("주문내역 조회 - 성공")
    @Test
    void findMemberOreders_success() {
        //given
        String accessToken = 액세스_토큰_가져옴();

        //when
        final ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .filter(document("orders-info-member-success"))
            .header("Authorization","Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("api/orders/me")
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
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
