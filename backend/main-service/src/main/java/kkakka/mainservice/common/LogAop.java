package kkakka.mainservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    @AfterThrowing(value = "execution(* kkakka.mainservice..*(..))", throwing = "e")
    public void checkException(JoinPoint joinPoint, Exception e) {
        log.warn("예외 발생지점 : {}", joinPoint.getSignature().toString());
        log.warn("------- StackTrace Start -------");
        for (StackTraceElement element : e.getStackTrace()) {
            log.warn("{}", element);
        }
        log.warn("------- StackTrace End -------");
    }
}
