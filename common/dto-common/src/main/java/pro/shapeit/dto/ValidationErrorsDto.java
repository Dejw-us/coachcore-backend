package pro.shapeit.dto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.*;

public record ValidationErrorsDto(
    Map<String, List<String>> errorMessages,
    String errorCode,
    Instant timestamp,
    String path
) {
  public static ValidationErrorsDto create(
      String errorCode,
      MethodArgumentNotValidException exception,
      HttpServletRequest request
  ) {
    var errors = new HashMap<String, List<String>>();

    for (var error : exception.getBindingResult().getFieldErrors()) {
      if (errors.containsKey(error.getField())) {
        var list = errors.get(error.getField());
        list.add(error.getDefaultMessage());
      } else {
        var list = new LinkedList<String>();
        list.add(error.getDefaultMessage());
        errors.put(error.getField(), list);
      }
    }

    return new ValidationErrorsDto(errors, errorCode, Instant.now(), request.getRequestURI());
  }
}
