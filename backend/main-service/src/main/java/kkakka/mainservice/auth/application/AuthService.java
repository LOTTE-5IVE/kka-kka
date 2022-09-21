package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.ui.dto.TokenDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    public TokenDto createToken(SocialProviderCodeDto socialProviderCodeDto) {
        return TokenDto.create("empty-token");
    }
}
