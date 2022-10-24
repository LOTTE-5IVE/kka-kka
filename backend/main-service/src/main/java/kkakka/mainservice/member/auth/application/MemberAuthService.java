package kkakka.mainservice.member.auth.application;

import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.auth.ui.dto.TokenDto;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthClient authClient;

    @Transactional
    public TokenDto createOrFindMember(SocialProviderCodeDto socialProviderCodeDto) {
        final UserProfile userProfile = getUserProfile(socialProviderCodeDto);

        final Member member = memberService.findOrCreateMember(userProfile);
        final String accessToken = jwtTokenProvider.generateToken(member.getId().toString());
        return TokenDto.create(accessToken);
    }

    private UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        return authClient.getUserProfile(socialProviderCodeDto);
    }
}
