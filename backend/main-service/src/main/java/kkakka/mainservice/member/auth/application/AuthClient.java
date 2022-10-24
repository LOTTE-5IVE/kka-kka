package kkakka.mainservice.member.auth.application;

import feign.Headers;
import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service", url = "${auth.client-url}")
public interface AuthClient {

    @PostMapping("/auth")
    @Headers("Content-Type: application/json;charset=utf-8")
    UserProfile getUserProfile(
            @RequestBody SocialProviderCodeDto socialProviderCodeDto
    );
}
