package pro.shapeit.backend.common.dto;

import jakarta.servlet.http.HttpServletRequest;
import pro.shapeit.backend.error.ErrorCode;

import java.time.Instant;

public record ErrorDto(
    String errorMessage,
    ErrorCode errorCode,
    Instant timestamp,
    String path
) {
  public static ErrorDto create(
      ErrorCode code,
      String message,
      HttpServletRequest request
  ) {
    return new ErrorDto(message, code, Instant.now(), request.getRequestURI());
  }
}
