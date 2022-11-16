package kkakka.mainservice;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static kkakka.mainservice.fixture.TestDataLoader.MEMBER;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import kkakka.mainservice.fixture.TestAdminLogin;
import kkakka.mainservice.fixture.feign.TestServiceInstanceListSupplier;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.ProviderName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {AcceptanceTest.TestConfig.class, TestAdminLogin.class})
@EmbeddedKafka
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;
    final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new TestServiceInstanceListSupplier("auth-service", 9001);
        }
    }

    @RegisterExtension
    public static WireMockExtension AUTH_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig()
                    .port(9001))
            .build();

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        RestAssured.port = port;

        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), ProviderName.TEST);

        AUTH_SERVICE.stubFor(
                post(urlEqualTo("/api/auth"))
                        .withRequestBody(equalTo(objectMapper.writeValueAsString(request)))
                        .willReturn(aResponse()
                                .withHeader("Content-Type",
                                        MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(HttpStatus.OK.value())
                                .withBody(
                                        objectMapper.writeValueAsString(
                                                new UserProfile(
                                                        MEMBER.getProvider(),
                                                        MEMBER.getName(),
                                                        MEMBER.getEmail(),
                                                        MEMBER.getAgeGroup(),
                                                        MEMBER.getPhone()
                                                )
                                        )
                                )
                        ));

        final SocialProviderCodeRequest wrong_request = SocialProviderCodeRequest.create(
                "wrong-value", ProviderName.TEST);

        AUTH_SERVICE.stubFor(
                post(urlEqualTo("/api/auth"))
                        .withRequestBody(
                                equalToJson(objectMapper.writeValueAsString(wrong_request)))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.BAD_REQUEST.value())
                        ));

    }
}

