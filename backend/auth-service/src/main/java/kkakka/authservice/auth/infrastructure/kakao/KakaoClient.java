package kkakka.authservice.auth.infrastructure.kakao;

import kkakka.authservice.auth.application.SocialClient;
import kkakka.authservice.auth.application.UserProfile;
import kkakka.authservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.authservice.auth.infrastructure.ClientResponseConverter;
import kkakka.authservice.auth.infrastructure.kakao.dto.KakaoTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoClient implements SocialClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String DEFAULT_CHARSET = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer %s";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final KakaoOauthInfo kakaoOauthInfo;

    @Override
    public UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final String accessToken = accessToken(socialProviderCodeDto.getCode());
        final HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, String.format(BEARER, accessToken));

        final ResponseEntity<String> response = restTemplate.exchange(
                kakaoOauthInfo.getProfileRequestUrl(), HttpMethod.GET, new HttpEntity<>(headers),
                String.class);
        validateResponseStatus(response);
        return converter.extractDataAsAccount(response.getBody(), KakaoUserProfile.class);
    }

    private String accessToken(String code) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE_HEADER, DEFAULT_CHARSET);

        final ResponseEntity<String> response = restTemplate.exchange(
                kakaoOauthInfo.getAccessTokenRequestUrl(), HttpMethod.POST, new HttpEntity<>(
                        converter.convertHttpBody(
                                new KakaoTokenRequest(GRANT_TYPE, kakaoOauthInfo.getRedirectUrl(),
                                        kakaoOauthInfo.getClientId(), kakaoOauthInfo.getClientKey(),
                                        code)), httpHeaders), String.class);
        validateResponseStatus(response);
        return converter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }
}
