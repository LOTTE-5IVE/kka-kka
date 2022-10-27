package kkakka.mainservice.member.auth.application;

import feign.Headers;
import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.auth.infrastructure.AuthErrorDecoder;
import kkakka.mainservice.member.auth.infrastructure.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service",
        configuration = {FeignConfig.class, AuthErrorDecoder.class})
public interface AuthClient {

    @PostMapping("/api/auth")
    @Headers("Content-Type: application/json;charset=utf-8")
    UserProfile getUserProfile(
            @RequestBody SocialProviderCodeDto socialProviderCodeDto
    );
}
