package pro.coachcore.training.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pro.coachcore.dto.ErrorsDto;
import pro.coachcore.exception.handler.DefaultGlobalExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler {
  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ResponseEntity<ErrorsDto> handleAuthenticationCredentialsNotFound(
      AuthenticationCredentialsNotFoundException exception,
      HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorsDto.create("NOT_AUTHORIZED", exception.getMessage(), request));
  }
}
