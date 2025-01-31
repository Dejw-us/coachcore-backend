package pro.shapeit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceFailedToUpdateException extends Exception {
  public ResourceFailedToUpdateException(String message) {
    super(message);
  }
}
