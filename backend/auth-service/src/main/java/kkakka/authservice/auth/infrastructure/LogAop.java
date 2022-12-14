package kkakka.authservice.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Profile("!test")
public class LogAop {

    @AfterThrowing(value = "execution(* kkakka.authservice..*(..))", throwing = "e")
    public void checkException(JoinPoint joinPoint, Exception e) {
        log.error("예외 발생지점 : {}", joinPoint.getSignature().toString());
        log.error("------- StackTrace Start -------");
        for (StackTraceElement element : e.getStackTrace()) {
            log.error("{}", element);
        }
        log.error("------- StackTrace End -------");
    }
}
