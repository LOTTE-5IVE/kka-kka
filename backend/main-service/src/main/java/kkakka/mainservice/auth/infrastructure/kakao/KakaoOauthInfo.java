package kkakka.mainservice.auth.infrastructure.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:bootstrap.yml")
@NoArgsConstructor
@Getter
public class KakaoOauthInfo {

    @Value("${kakao.url.access-token}")
    private String accessTokenRequestUrl;
    @Value("${kakao.url.profile}")
    private String profileRequestUrl;
    @Value("${kakao.url.redirect-uri}")
    private String redirectUrl;
    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.client.key}")
    private String clientKey;
    @Value("${kakao.state}")
    private String state;
}
