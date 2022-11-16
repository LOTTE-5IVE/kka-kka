package kkakka.mainservice.admin.acceptance;

import static kkakka.mainservice.fixture.TestAdminUser.TEST_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import kkakka.mainservice.DocumentConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AdminAcceptanceTest extends DocumentConfiguration {

    @DisplayName("관리자 로그인 - 성공")
    @Test
    void adminLogin() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("userId", TEST_ADMIN.getUserId());
        request.put("password", TEST_ADMIN.getPassword());

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("admin-login-success"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/admin/login")
                .then().extract();

        // then
        assertThat(response.body().jsonPath().get("adminToken").toString()).isNotNull();
    }

    @DisplayName("관리자 로그인 - 실패")
    @Test
    void adminLogin_fail() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("userId", "wrong-id");
        request.put("password", "password-id");

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("admin-login-fail"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/admin/login")
                .then().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
