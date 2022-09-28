package kkakka.mainservice.member.auth.ui;

import kkakka.mainservice.member.auth.application.AuthService;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<Void> login(SocialProviderCodeRequest socialProviderCodeRequest) {
        authService.authenticateUser(socialProviderCodeRequest.toDto());
        return ResponseEntity.ok().build();
    }
}
