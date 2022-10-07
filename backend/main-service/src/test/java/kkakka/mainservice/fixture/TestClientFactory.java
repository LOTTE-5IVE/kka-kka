package kkakka.mainservice.fixture;

import kkakka.mainservice.auth.application.SocialClient;
import kkakka.mainservice.auth.application.SocialLoginStrategyFactory;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.domain.ProviderName;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestClientFactory implements SocialLoginStrategyFactory {

    @Override
    public SocialClient mapSocialClient(ProviderName providerName) {
        return new SocialClient() {
            @Override
            public UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
                return TestMember.findByCode(socialProviderCodeDto.getCode())
                        .getUserProfile();
            }
        };
    }
}
