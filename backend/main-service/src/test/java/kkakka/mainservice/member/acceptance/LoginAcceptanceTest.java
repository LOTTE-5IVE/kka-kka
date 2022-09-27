package kkakka.mainservice.member.acceptance;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.AcceptanceTest;
import kkakka.mainservice.auth.domain.ProviderName;
import kkakka.mainservice.auth.ui.dto.SocialProviderCodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LoginAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입과 토큰 발급 - 성공")
    @Test
    void joinSuccess() {
        // given
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                MEMBER_01.getCode(), ProviderName.TEST);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/auth/login/token")
                .then().log().all().extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("로그인과 토큰 발급 - 성공")
    @Test
    void loginSuccess(){
        // given
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                MEMBER_01.getCode(), ProviderName.TEST);
        회원가입_요청(request);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/auth/login/token")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    private void 회원가입_요청(SocialProviderCodeRequest request) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/auth/login/token")
                .then().extract();
    }
}
