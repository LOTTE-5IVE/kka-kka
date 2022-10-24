package kkakka.mainservice.member.auth.application;

import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.auth.infrastructure.ResponseConverter;
import kkakka.mainservice.member.auth.ui.dto.TokenDto;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;
    private final ResponseConverter converter;

    @Transactional
    public TokenDto createOrFindMember(SocialProviderCodeDto socialProviderCodeDto) {
        final UserProfile userProfile = getUserProfile(socialProviderCodeDto);

        final Member member = memberService.findOrCreateMember(userProfile);
        final String accessToken = jwtTokenProvider.generateToken(member.getId().toString());
        return TokenDto.create(accessToken);
    }

    private UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        final ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:9001/api/auth",
                HttpMethod.POST,
                new HttpEntity<>(
                        socialProviderCodeDto,
                        headers
                ),
                String.class
        );

        return converter.extractDataAsAccount(response.getBody(),
                UserProfile.class);
    }
}
