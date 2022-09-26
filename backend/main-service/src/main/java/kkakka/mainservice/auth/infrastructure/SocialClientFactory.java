package kkakka.mainservice.auth.infrastructure;

import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import kkakka.mainservice.auth.application.SocialClient;
import kkakka.mainservice.auth.application.SocialLoginStrategyFactory;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.domain.ProviderName;
import kkakka.mainservice.auth.infrastructure.kakao.KakaoClient;
import kkakka.mainservice.auth.infrastructure.kakao.KakaoOauthInfo;
import kkakka.mainservice.auth.infrastructure.naver.NaverClient;
import kkakka.mainservice.auth.infrastructure.naver.NaverOauthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile({"dev", "prod", "local"})
@RequiredArgsConstructor
public class SocialClientFactory implements SocialLoginStrategyFactory {

    private final ClientResponseConverter clientResponseConverter;
    private final RestTemplate restTemplate;
    private final NaverOauthInfo naverOauthInfo;
    private final KakaoOauthInfo kakaoOauthInfo;

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
    }

    @Override
    public SocialClient mapSocialClient(ProviderName providerName) {
        return socialClients.get(providerName);
    }
}