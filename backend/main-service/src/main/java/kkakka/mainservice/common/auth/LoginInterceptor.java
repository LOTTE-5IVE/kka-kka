package kkakka.mainservice.common.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kkakka.mainservice.common.auth.util.AuthorizationExtractor;
import kkakka.mainservice.common.auth.util.JwtTokenProvider;
import kkakka.mainservice.common.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return true;
        }

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new AuthorizationException();
    }
}
