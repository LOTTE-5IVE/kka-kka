package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.domain.ProviderName;

public interface SocialLoginStrategyFactory {

    SocialClient mapSocialClient(ProviderName providerName);
}