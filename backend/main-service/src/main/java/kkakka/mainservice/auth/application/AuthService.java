package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.naver.NaverClient;
import kkakka.mainservice.auth.infrastructure.NaverUserProfile;
import kkakka.mainservice.auth.ui.dto.TokenDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthService {

    private final NaverClient naverClient;
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    public TokenDto createToken(SocialProviderCodeDto socialProviderCodeDto) {
        final NaverUserProfile userDetail = naverClient.getUserDetail(socialProviderCodeDto);
        return TokenDto.create("empty-token");
    }
}
