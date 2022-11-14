package kkakka.mainservice.common.auth.aop;

import java.util.Arrays;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.common.exception.AuthorizationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAop {

    @Before("@annotation(kkakka.mainservice.common.auth.aop.MemberOnly) "
            + "|| @within(kkakka.mainservice.common.auth.aop.MemberOnly)")
    public void checkLoginMember(JoinPoint joinPoint) {
        final LoginMember loginMember = (LoginMember) Arrays.stream(joinPoint.getArgs())
                .filter(argument -> argument instanceof LoginMember)
                .findAny()
                .orElseThrow(AuthorizationException::new);

        if (loginMember.isAnonymous()) {
            throw new AuthorizationException();
        }
    }

    @Before("@annotation(kkakka.mainservice.common.auth.aop.AdminOnly) "
            + "|| @within(kkakka.mainservice.common.auth.aop.AdminOnly)")
    public void checkAdmin(JoinPoint joinPoint) {
        final LoginMember loginMember = (LoginMember) Arrays.stream(joinPoint.getArgs())
                .filter(argument -> argument instanceof LoginMember)
                .findAny()
                .orElseThrow(AuthorizationException::new);

        if (!loginMember.isAdmin()) {
            throw new AuthorizationException();
        }
    }
}
