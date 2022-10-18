package kkakka.mainservice.member;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kkakka.mainservice.AcceptanceTest;
import kkakka.mainservice.common.config.WebMvcConfig;
import kkakka.mainservice.member.auth.infrastructure.LoginInterceptor;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.util.AuthenticationPrincipalArgumentResolver;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AuthorityAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @TestConfiguration
    public static class TestConfig {

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Bean
        public LoginInterceptor testInterceptor() {
            return new LoginInterceptor(jwtTokenProvider) {
            };
        }

        @Bean
        public AuthenticationPrincipalArgumentResolver testAuthenticationResolver() {
            return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider) {
            };
        }

        @Bean
        public WebMvcConfigurer interceptorConfigure() {
            return new WebMvcConfig(jwtTokenProvider, testInterceptor()) {
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
        final String accessToken = jwtTokenProvider.generateToken(TEST_MEMBER_01.getAccessToken());
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
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
