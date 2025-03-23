package pro.coachcore.profile.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.coachcore.exception.handler.DefaultGlobalExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
}
