package kkakka.mainservice.auth.infrastructure;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class NaverClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer ";
    private static final String code = "";
    private static final String mytoken = "";
    private static final Logger log = LoggerFactory.getLogger(NaverClient.class);

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final NaverOauthInfo naverOauthInfo;

    public NaverUserProfile getUserDetail() {
        // TODO: 실제 토큰으로 바꾸기
        // final String accessToken = getAccessToken();

        final String accessToken = mytoken;
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", BEARER + accessToken);

        final ResponseEntity<String> response = restTemplate.exchange(
                naverOauthInfo.getProfileRequestUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        return converter.extractDataAsAccount(response.getBody());
    }

    public String getAccessToken() {
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
                        ))
                ),
                String.class
        );
        return converter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }
}
