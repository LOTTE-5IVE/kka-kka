package kkakka.mainservice.auth.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class NaverClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String code = "";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final NaverOauthInfo naverOauthInfo;

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
