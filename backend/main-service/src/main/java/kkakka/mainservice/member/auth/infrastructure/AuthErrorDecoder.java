package kkakka.mainservice.member.auth.infrastructure;

import feign.Response;
import feign.codec.ErrorDecoder;
import kkakka.mainservice.common.exception.InvalidSocialAccountException;
import kkakka.mainservice.common.exception.SocialServiceException;

public class AuthErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            return new InvalidSocialAccountException();
        }
        if (response.status() == 500) {
            return new SocialServiceException();
        }
        return new RuntimeException();
    }
}
