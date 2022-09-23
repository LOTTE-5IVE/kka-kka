package kkakka.mainservice.auth.infrastructure.naver;

import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.ClientResponseConverter;
import kkakka.mainservice.auth.infrastructure.NaverUserProfile;
import kkakka.mainservice.auth.infrastructure.naver.dto.NaverTokenRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class NaverClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer ";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final NaverOauthInfo naverOauthInfo;

    public NaverUserProfile getUserDetail(SocialProviderCodeDto socialProviderCodeDto) {
        final String accessToken = getAccessToken(socialProviderCodeDto.getCode());
        final HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER + accessToken);

        final ResponseEntity<String> response = restTemplate.exchange(
                naverOauthInfo.getProfileRequestUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        return converter.extractDataAsAccount(response.getBody());
    }

    public String getAccessToken(String code) {
        final HttpHeaders headers = new HttpHeaders();

        final ResponseEntity<String> response = restTemplate.exchange(
                naverOauthInfo.getAccessTokenRequestUrl(),
                HttpMethod.POST,
                new HttpEntity<>(
                        converter.convertHttpBody(new NaverTokenRequest(
                                GRANT_TYPE,
                                naverOauthInfo.getClientId(),
                                naverOauthInfo.getClientKey(),
                                code,
                                naverOauthInfo.getState()
                        )),
                        headers
                ),
                String.class
        );
        return converter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }
}
