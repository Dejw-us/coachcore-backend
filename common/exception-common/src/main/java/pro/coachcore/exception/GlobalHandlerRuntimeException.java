package pro.coachcore.exception;

import org.springframework.http.HttpStatus;

public abstract class GlobalHandlerRuntimeException extends RuntimeException {
  private final HttpStatus status;

  public GlobalHandlerRuntimeException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public abstract String getErrorCode();

  public HttpStatus getStatus() {
    return status;
  }
}
