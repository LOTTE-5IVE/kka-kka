package kkakka.mainservice.member.auth.ui;

import kkakka.mainservice.member.auth.application.MemberAuthService;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.auth.ui.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenDto> login(
            @RequestBody SocialProviderCodeRequest socialProviderCodeRequest
    ) {
        final TokenDto tokenDto = memberAuthService.createOrFindMember(
                socialProviderCodeRequest.toDto());
        return ResponseEntity.ok().body(tokenDto);
    }
}
