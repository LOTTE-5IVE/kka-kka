package kkakka.mainservice.auth.infrastructure.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:bootstrap.yml")
@NoArgsConstructor
@Getter
public class NaverOauthInfo {

    @Value("${naver.url.access-token}")
    private String accessTokenRequestUrl;
    @Value("${naver.url.profile}")
    private String profileRequestUrl;
    @Value("${naver.client.id}")
    private String clientId;
    @Value("${naver.client.key}")
    private String clientKey;
    @Value("${naver.state}")
    private String state;
}
