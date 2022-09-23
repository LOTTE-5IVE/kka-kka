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

    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() {
        // given
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                MEMBER_01.getCode(), ProviderName.TEST);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/auth/login/token")
                .then().log().all().extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }
}
