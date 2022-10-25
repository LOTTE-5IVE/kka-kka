package kkakka.authservice.auth.ui;

import kkakka.authservice.auth.application.AuthService;
import kkakka.authservice.auth.application.SocialAuthenticateException;
import kkakka.authservice.auth.application.dto.UserProfileDto;
import kkakka.authservice.auth.ui.dto.SocialProviderCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(SocialAuthenticateException.class)
    public ResponseEntity<Void> authenticateExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }
}
