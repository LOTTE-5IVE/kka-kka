package kkakka.authservice.auth.application;

import kkakka.authservice.auth.application.dto.SocialProviderCodeDto;
import org.springframework.http.ResponseEntity;

public interface SocialClient {

    UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto);

    default void validateResponseStatus(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new SocialAuthenticateException();
        }
    }
}
