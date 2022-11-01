package kkakka.authservice.auth.application;

import kkakka.authservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.authservice.auth.application.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final SocialLoginStrategyFactory socialLoginStrategyFactory;

    @Transactional
    public UserProfileDto getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final SocialClient socialClient = socialLoginStrategyFactory.mapSocialClient(
                socialProviderCodeDto.getProviderName());
        final UserProfile userProfile = socialClient.getUserProfile(socialProviderCodeDto);
        return userProfile.toDto();
    }
}
