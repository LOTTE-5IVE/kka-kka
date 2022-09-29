package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;

public interface SocialClient {

    UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto);
}
