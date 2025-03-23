package pro.coachcore.exception;

import org.springframework.http.HttpStatus;

public abstract class GlobalHandlerRuntimeException extends RuntimeException {
  private final HttpStatus status;

  public GlobalHandlerRuntimeException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public static GlobalHandlerRuntimeException create(String message, HttpStatus status,
      String errorCode) {
    return new GlobalHandlerRuntimeException(message, status) {
      @Override
      public String getErrorCode() {
        return errorCode;
      }
    };
  }

  public abstract String getErrorCode();

  public HttpStatus getStatus() {
    return status;
  }
}
