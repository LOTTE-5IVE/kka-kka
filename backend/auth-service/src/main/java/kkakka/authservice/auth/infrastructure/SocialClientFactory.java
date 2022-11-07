package kkakka.authservice.auth.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import kkakka.authservice.auth.application.SocialClient;
import kkakka.authservice.auth.application.SocialLoginStrategyFactory;
import kkakka.authservice.auth.domain.ProviderName;
import kkakka.authservice.auth.infrastructure.google.GoogleClient;
import kkakka.authservice.auth.infrastructure.google.GoogleOauthInfo;
import kkakka.authservice.auth.infrastructure.kakao.KakaoClient;
import kkakka.authservice.auth.infrastructure.kakao.KakaoOauthInfo;
import kkakka.authservice.auth.infrastructure.naver.NaverClient;
import kkakka.authservice.auth.infrastructure.naver.NaverOauthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SocialClientFactory implements SocialLoginStrategyFactory {

    private final ClientResponseConverter clientResponseConverter;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final NaverOauthInfo naverOauthInfo;
    private final KakaoOauthInfo kakaoOauthInfo;
    private final GoogleOauthInfo googleOauthInfo;

    private Map<ProviderName, SocialClient> socialClients;

    @PostConstruct
    private void initialize() {
        socialClients = new EnumMap<>(ProviderName.class);
        socialClients.put(
                ProviderName.NAVER,
                new NaverClient(clientResponseConverter, restTemplate, naverOauthInfo)
        );
        socialClients.put(
                ProviderName.KAKAO,
                new KakaoClient(clientResponseConverter, restTemplate, kakaoOauthInfo)
        );
        socialClients.put(
                ProviderName.GOOGLE,
                new GoogleClient(clientResponseConverter, restTemplate, googleOauthInfo,
                        objectMapper)
        );
    }

    @Override
    public SocialClient mapSocialClient(ProviderName providerName) {
        return socialClients.get(providerName);
    }
}