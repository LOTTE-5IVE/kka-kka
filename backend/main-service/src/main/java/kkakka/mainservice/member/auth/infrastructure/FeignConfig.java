package kkakka.mainservice.member.auth.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public AuthErrorDecoder authErrorDecoder() {
        return new AuthErrorDecoder();
    }
}
