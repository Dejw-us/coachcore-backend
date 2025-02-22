package pro.coachcore.util;

import lombok.experimental.UtilityClass;
import java.util.function.Consumer;

@UtilityClass
public class ServiceUtils {
  public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
    if (value != null) {
      setter.accept(value);
    }
  }
}
