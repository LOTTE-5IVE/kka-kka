package kkakka.mainservice.auth.infrastructure.google;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
public class GoogleOauthInfo {

    @Value("${google.url.access-token}")
    private String accessTokenRequestUrl;
    @Value("${google.url.profile}")
    private String profileRequestUrl;
    @Value("${google.url.redirect-uri}")
    private String redirectUri;
    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.client.key}")
    private String clientKey;
    @Value("${google.state}")
    private String state;
    @Value("${google.scope}")
    private String scope;
}
