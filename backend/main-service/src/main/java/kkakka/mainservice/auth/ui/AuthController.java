package kkakka.mainservice.auth.ui;

import kkakka.mainservice.auth.application.AuthService;
import kkakka.mainservice.auth.application.dto.UserProfileDto;
import kkakka.mainservice.auth.ui.dto.SocialProviderCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserProfileDto> authenticateSocialUser(
            @RequestBody SocialProviderCodeRequest socialProviderCodeRequest
    ) {
        final UserProfileDto userProfileDto = authService.getUserProfile(
                socialProviderCodeRequest.toDto());
        return ResponseEntity.ok().body(userProfileDto);
    }
}
