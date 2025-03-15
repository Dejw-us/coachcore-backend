package pro.coachcore.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@UtilityClass
public class ServiceUtils {
  public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
    if (value != null) {
      log.debug("updated: {}", value.getClass().getName());
      setter.accept(value);
    }
  }
}
