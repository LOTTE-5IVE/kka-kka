package kkakka.authservice.auth.application;

import kkakka.authservice.auth.domain.ProviderName;

public interface SocialLoginStrategyFactory {

    SocialClient mapSocialClient(ProviderName providerName);
}