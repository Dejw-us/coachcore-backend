package pro.shapeit.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.shapeit.dto.ErrorsDto;

@Hidden
@RestControllerAdvice
public class DefaultGlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorsDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception,
      HttpServletRequest request
  ) {
    var errors = exception.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    return ResponseEntity
        .badRequest()
        .body(ErrorsDto.create("DTO_VALIDATION_ERROR", errors, request));
  }

  @ExceptionHandler(GlobalHandlerRuntimeException.class)
  public ResponseEntity<ErrorsDto> handleGlobalHandlerRuntimeException(
      GlobalHandlerRuntimeException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(exception.getStatus())
        .body(ErrorsDto.create("GENERIC_ERROR", exception.getMessage(), request));
  }
}
