package pro.shapeit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends GlobalHandlerRuntimeException {
  public ResourceNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }

  public static Supplier<ResourceNotFoundException> supplier(String message) {
    return () -> new ResourceNotFoundException(message);
  }
}
