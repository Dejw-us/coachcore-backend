package pro.coachcore.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import pro.coachcore.dto.ErrorsDto;
import pro.coachcore.dto.ValidationErrorsDto;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class DefaultGlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorsDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .badRequest()
        .body(ValidationErrorsDto.create("DTO_VALIDATION_ERROR", exception, request));
  }

  @ExceptionHandler(GlobalHandlerRuntimeException.class)
  public ResponseEntity<ErrorsDto> handleGlobalHandlerRuntimeException(
      GlobalHandlerRuntimeException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(exception.getStatus())
        .body(ErrorsDto.create(exception.getErrorCode(), exception.getMessage(), request));
  }
}
