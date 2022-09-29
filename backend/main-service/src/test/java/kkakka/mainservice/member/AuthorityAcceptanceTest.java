package kkakka.mainservice.member;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.AcceptanceTest;
import kkakka.mainservice.common.config.WebMvcConfig;
import kkakka.mainservice.fixture.TestMember;
import kkakka.mainservice.member.auth.infrastructure.LoginInterceptor;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AuthorityAcceptanceTest extends AcceptanceTest {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public LoginInterceptor testInterceptor() {
            return new LoginInterceptor(
                    testTokenProvider()
            );
        }

        @Bean
        public JwtTokenProvider testTokenProvider() {
            return new JwtTokenProvider() {
                @Override
                public boolean validateToken(String token) {
                    return TestMember.findByAccessToken(token);
                }
            };
        }

        @Bean
        public WebMvcConfigurer interceptorConfigure() {
            return new WebMvcConfig(testTokenProvider(), testInterceptor()) {
                @Override
                public void addInterceptors(InterceptorRegistry registry) {
                    registry.addInterceptor(testInterceptor())
                            .addPathPatterns("/**");
                }
            };
        }

        @RestController
        public static class TestController {

            @PostMapping("/test/authority")
            public ResponseEntity<Void> testMemberAuthority(
                    @AuthenticationPrincipal LoginMember loginMember) {
                return ResponseEntity.ok().build();
            }
        }
    }

    @DisplayName("사용자 권한 테스트 - 성공")
    @Test
    void userAuthority_success() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + MEMBER_01.getAccessToken())
                .when()
                .post("/api/test/authority")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("사용자 권한 테스트 - 실패")
    @Test
    void userAuthority_fail() {
        // given
        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .post("/api/test/authority")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
