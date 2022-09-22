package kkakka.mainservice.auth.ui;

import kkakka.mainservice.auth.application.AuthService;
import kkakka.mainservice.auth.ui.dto.CodeRequest;
import kkakka.mainservice.auth.ui.dto.SocialProviderCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/token")
    public ResponseEntity<Void> login(
//            @RequestBody CodeRequest codeRequest
    ) {
        authService.createToken();
        return ResponseEntity.ok().build();
    }
}
