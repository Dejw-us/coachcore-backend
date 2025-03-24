package pro.coachcore.oauth2.server.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.coachcore.exception.handler.DefaultGlobalExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
}
