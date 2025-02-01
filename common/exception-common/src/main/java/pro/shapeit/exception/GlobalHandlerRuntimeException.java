package pro.shapeit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GlobalHandlerRuntimeException extends RuntimeException {
  private final HttpStatus status;

  public GlobalHandlerRuntimeException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public abstract String getErrorCode();
}
