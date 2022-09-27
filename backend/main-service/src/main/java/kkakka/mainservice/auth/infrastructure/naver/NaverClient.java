package kkakka.mainservice.auth.infrastructure.naver;

import kkakka.mainservice.auth.application.SocialClient;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.ClientResponseConverter;
import kkakka.mainservice.auth.infrastructure.naver.dto.NaverTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverClient implements SocialClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer %s";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final NaverOauthInfo naverOauthInfo;

    public UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final String accessToken = getAccessToken(socialProviderCodeDto.getCode());
        final HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, String.format(BEARER, accessToken));

        final ResponseEntity<String> response = restTemplate.exchange(
                naverOauthInfo.getProfileRequestUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        return converter.extractDataAsAccount(response.getBody(), NaverUserProfile.class);
    }

    private String getAccessToken(String code) {
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
