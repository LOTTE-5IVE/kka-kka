package kkakka.mainservice.member.auth.util;

import java.util.Arrays;
import kkakka.mainservice.member.auth.exception.AuthorizationException;
import kkakka.mainservice.member.auth.ui.LoginMember;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAop {

    @Before("@annotation(kkakka.mainservice.member.auth.ui.MemberOnly) "
            + "|| @within(kkakka.mainservice.member.auth.ui.MemberOnly)")
    public void checkLoginMember(JoinPoint joinPoint) {
        final LoginMember loginMember = (LoginMember) Arrays.stream(joinPoint.getArgs())
                .filter(argument -> argument instanceof LoginMember)
                .findAny()
                .orElseThrow(AuthorizationException::new);

        if (loginMember.isAnonymous()) {
            throw new AuthorizationException();
        }
    }
}
