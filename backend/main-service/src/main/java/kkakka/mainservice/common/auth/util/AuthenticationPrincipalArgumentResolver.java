package kkakka.mainservice.common.auth.util;

import javax.servlet.http.HttpServletRequest;
import kkakka.mainservice.common.auth.AuthenticationPrincipal;
import kkakka.mainservice.common.auth.Authority;
import kkakka.mainservice.common.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        final String credentials = AuthorizationExtractor.extract(
                webRequest.getNativeRequest(HttpServletRequest.class));

        if (!jwtTokenProvider.validateToken(credentials)) {
            return new LoginMember(Authority.ANONYMOUS);
        }

        try {
            final Long id = Long.parseLong(jwtTokenProvider.getPayload(credentials));
            return new LoginMember(id, Authority.MEMBER);
        } catch (NumberFormatException e) {
            return new LoginMember(Authority.ADMIN);
        }
    }
}
