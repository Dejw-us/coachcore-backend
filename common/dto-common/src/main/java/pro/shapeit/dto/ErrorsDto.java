package pro.shapeit.dto;

import jakarta.servlet.http.HttpServletRequest;
import pro.shapeit.error.ErrorCode;

import java.time.Instant;
import java.util.List;

public record ErrorsDto(
    List<String> errorMessages,
    ErrorCode errorCode,
    Instant timestamp,
    String path
) {
  public static ErrorsDto create(
      ErrorCode code,
      List<String> errorMessages,
      HttpServletRequest request
  ) {
    return new ErrorsDto(errorMessages, code, Instant.now(), request.getRequestURI());
  }

  public static ErrorsDto create(
      ErrorCode code,
      String message,
      HttpServletRequest request
  ) {
    return new ErrorsDto(List.of(message), code, Instant.now(), request.getRequestURI());
  }
}
