package pro.coachcore.dto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.*;

public record ValidationErrorsDto(
    List<ValidationErrorDto> errors,
    String errorCode,
    Instant timestamp,
    String path
) {
  public static ValidationErrorsDto create(
      String errorCode,
      MethodArgumentNotValidException exception,
      HttpServletRequest request
  ) {
    var errors = new ArrayList<ValidationErrorDto>();

    for (var fieldError : exception.getBindingResult().getFieldErrors()) {
      var error = errors.stream()
          .filter(err -> err.field().equals(fieldError.getField()))
          .findFirst()
          .orElseGet(() -> {
            var dto = new ValidationErrorDto(fieldError.getField(), new ArrayList<>());
            errors.add(dto);
            return dto;
          });

      error.errorMessages().add(fieldError.getDefaultMessage());
    }

    return new ValidationErrorsDto(errors, errorCode, Instant.now(), request.getRequestURI());
  }
}
