package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.JwtTokenProvider;
import kkakka.mainservice.auth.ui.dto.TokenDto;
import kkakka.mainservice.member.application.MemberService;
import kkakka.mainservice.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthService {

    private final SocialLoginStrategyFactory socialLoginStrategyFactory;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Transactional
    public TokenDto createToken(SocialProviderCodeDto socialProviderCodeDto) {
        final SocialClient socialClient = socialLoginStrategyFactory.mapSocialClient(
                socialProviderCodeDto.getProviderName());
        final UserProfile userProfile = socialClient.getUserProfile(socialProviderCodeDto);

        final Member member = memberService.findOrCreateMember(userProfile);
        return TokenDto.create(jwtTokenProvider.generateToken(member.getId().toString()));
    }
}
