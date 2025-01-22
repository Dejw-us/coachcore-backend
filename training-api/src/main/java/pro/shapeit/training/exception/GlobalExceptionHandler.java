package pro.shapeit.training.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.shapeit.common.dto.ErrorsDto;
import pro.shapeit.common.error.ErrorCode;
import pro.shapeit.common.exception.ResourceAlreadyExistsException;
import pro.shapeit.common.exception.ResourceFailedToUpdateException;
import pro.shapeit.common.exception.ResourceNotFoundException;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorsDto> handleResourceNotFound(
      ResourceNotFoundException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorsDto.create(ErrorCode.RESOURCE_NOT_FOUND, exception.getMessage(), request));
  }

  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ResponseEntity<ErrorsDto> handleAuthenticationCredentialsNotFound(
      AuthenticationCredentialsNotFoundException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorsDto.create(ErrorCode.NOT_AUTHORIZED, exception.getMessage(), request));
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ErrorsDto> handleResourceAlreadyExistsException(
      ResourceAlreadyExistsException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorsDto.create(ErrorCode.RESOURCE_ALREADY_EXISTS, exception.getMessage(), request));
  }

  @ExceptionHandler(ResourceFailedToUpdateException.class)
  public ResponseEntity<ErrorsDto> handleResourceFailedToUpdateException(
      ResourceFailedToUpdateException exception,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorsDto.create(ErrorCode.RESOURCE_FAILED_TO_UPDATE, exception.getMessage(), request));
  }

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
        .body(ErrorsDto.create(ErrorCode.DTO_VALIDATION_ERROR, errors, request));
  }
}
