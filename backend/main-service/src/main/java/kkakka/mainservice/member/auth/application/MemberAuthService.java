package kkakka.mainservice.member.auth.application;

import kkakka.mainservice.auth.application.dto.UserProfileDto;
import kkakka.mainservice.auth.domain.ProviderName;
import kkakka.mainservice.auth.ui.AuthController;
import kkakka.mainservice.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.auth.ui.dto.TokenDto;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    // TODO: Controller 직접 참조 대신 MSA 통신으로 변경
    private final AuthController authController;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenDto createOrFindMember(SocialProviderCodeDto socialProviderCodeDto) {
        final UserProfile userProfile = authenticateUser(socialProviderCodeDto);

        final Member member = memberService.findOrCreateMember(userProfile);
        final String accessToken = jwtTokenProvider.generateToken(member.getId().toString());
        return TokenDto.create(accessToken);
    }

    private UserProfile authenticateUser(SocialProviderCodeDto socialProviderCodeDto) {
        // TODO: 의존성 분리하고 auth 서버에 직접 요청보내도록 변경
        final ResponseEntity<UserProfileDto> response = authController.authenticateSocialUser(
                SocialProviderCodeRequest.create(
                        socialProviderCodeDto.getCode(),
                        ProviderName.valueOf(
                                socialProviderCodeDto.getMemberProviderName().toString()
                        )
                )
        );

        final UserProfileDto userProfileDto = response.getBody();

        // TODO: UserProfileDto 의존성 제거하고 직접 UserProfile 로 consume
        return new UserProfile(
                Provider.create(
                        userProfileDto.getProvider().getProviderId(),
                        MemberProviderName.valueOf(
                                userProfileDto.getProvider().getProviderName().toString()
                        )
                ),
                userProfileDto.getName(),
                userProfileDto.getEmail(),
                userProfileDto.getAgeGroup(),
                userProfileDto.getPhone()
        );
    }
}
