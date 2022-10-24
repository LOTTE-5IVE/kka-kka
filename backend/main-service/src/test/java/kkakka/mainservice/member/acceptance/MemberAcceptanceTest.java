package kkakka.mainservice.member.acceptance;

import static kkakka.mainservice.fixture.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberAcceptanceTest extends DocumentConfiguration {

    @DisplayName("회원 정보 조회 - 성공")
    @Test
    void showMemberInfo_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("member-info"))
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat((String) response.path("name")).isEqualTo(MEMBER.getName());
    }

    @DisplayName("회원 정보 조회 - 실패(토큰 없음)")
    @Test
    void showMemberInfo_fail() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원 정보 수정 요청(이름 수정) - 성공")
    @Test
    void editMemberInfo_name_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();
        final String newName = "새로운이름";

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("member-info-update-name"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(String.format("{\"name\": \"%s\"}", newName))
                .when()
                .patch("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보 수정 요청(이메일, 연락처) - 성공")
    @Test
    void editMemberInfo_email_phone_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();
        final String newMail = "validname@test.com";
        final String newPhone = "010-0000-0001";

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("member-info-update-email-phone"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(String.format("{\"email\": \"%s\", \"phone\": \"%s\"}", newMail, newPhone))
                .when()
                .patch("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보 수정 요청(이메일, 연락처) - 실패")
    @Test
    void editMemberInfo_email_phone_fail() {
        // given
        final String accessToken = 액세스_토큰_가져옴();
        final String newMail = "invalid-email";
        final String newPhone = "010-0000-0001";

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given(spec).log().all()
                .filter(document("member-info-update-email-phone-fail"))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(String.format("{\"email\": \"%s\", \"phone\": \"%s\"}", newMail, newPhone))
                .when()
                .patch("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        final ExtractableResponse<Response> memberInfoResponse = 회원_정보_가져옴(accessToken);
        assertThat((String) memberInfoResponse.path("email")).isNotEqualTo(newMail);
    }

    private ExtractableResponse<Response> 회원_정보_가져옴(String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/members/me")
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
