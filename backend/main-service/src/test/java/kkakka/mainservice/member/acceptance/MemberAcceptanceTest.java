package kkakka.mainservice.member.acceptance;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.DocumentConfiguration;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberAcceptanceTest extends DocumentConfiguration {

    @DisplayName("사용자 검증이 필요한 요청 - 토큰 있음")
    @Test
    void showMemberInfo_success() {
        // given
        final String accessToken = 액세스_토큰_가져옴();

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/members/me")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("사용자 검증이 필요한 요청 - 토큰 없음")
    @Test
    void showMemberInfo_fail() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .post("/api/members/me")
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

    // TODO: 회원 정보 조회 API 구현한 뒤 이어서 작성
    @Disabled
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
