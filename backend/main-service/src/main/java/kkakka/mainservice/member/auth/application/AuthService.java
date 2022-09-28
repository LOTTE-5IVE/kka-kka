package kkakka.mainservice.member.auth.application;

import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    public void authenticateUser(SocialProviderCodeDto toDto) {

    }
}
