package pro.coachcore.util;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceUtils {
  public static <T> boolean updateIfNotNull(T value, Consumer<T> setter) {
    if (value != null) {
      setter.accept(value);
      return true;
    }
    return false;
  }

  public static <T> boolean updateIf(T value, Consumer<T> setter, Function<T, Boolean> validator) {
    if (value != null && validator.apply(value)) {
      setter.accept(value);
      return true;
    }
    return false;
  }
}
