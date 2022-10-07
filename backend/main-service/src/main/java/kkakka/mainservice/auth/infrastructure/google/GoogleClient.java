package kkakka.mainservice.auth.infrastructure.google;

import kkakka.mainservice.auth.application.SocialClient;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.ClientResponseConverter;
import kkakka.mainservice.auth.infrastructure.google.dto.GoogleTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleClient implements SocialClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC = "Basic %s";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String DEFAULT_CHARSET = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer %s";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final GoogleOauthInfo googleOauthInfo;


    @Override
    public UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final String accessToken = accessToken(socialProviderCodeDto.getCode());

        return null;
    }

    private String accessToken(String code) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE_HEADER, DEFAULT_CHARSET);

        final ResponseEntity<String> response = restTemplate.exchange(
                googleOauthInfo.getAccessTokenRequestUrl(), HttpMethod.POST,
                new HttpEntity<>(
                        converter.convertHttpBody(
                                new GoogleTokenRequest(
                                        GRANT_TYPE,
                                        googleOauthInfo.getRedirectUri(),
                                        googleOauthInfo.getClientId(),
                                        googleOauthInfo.getClientKey(),
                                        code
                                )
                        ),
                        httpHeaders), String.class
        );
        return converter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }
}
