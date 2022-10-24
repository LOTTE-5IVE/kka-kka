package kkakka.authservice.auth.application;

import kkakka.authservice.auth.application.dto.SocialProviderCodeDto;

public interface SocialClient {

    UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto);
}
