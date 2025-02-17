package pro.coachcore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceFailedToUpdateException extends GlobalHandlerRuntimeException {
  public ResourceFailedToUpdateException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }

  @Override
  public String getErrorCode() {
    return "RESOURCE_FAILED_TO_UPDATE";
  }
}
