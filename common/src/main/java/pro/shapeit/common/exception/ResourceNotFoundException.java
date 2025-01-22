package pro.shapeit.common.exception;

import java.util.function.Supplier;

public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException(String message) {
    super(message);
  }

  public static Supplier<ResourceNotFoundException> supplier(String message) {
    return () -> new ResourceNotFoundException(message);
  }
}
