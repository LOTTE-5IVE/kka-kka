package kkakka.mainservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KkaKkaException.class)
    public ResponseEntity<Void> globalKkaKkaExceptionHandler(KkaKkaException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Void> AuthorityExceptionHandler(AuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<Void> AuthenticateFailExceptionHandler(LoginFailException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> internalExceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}
