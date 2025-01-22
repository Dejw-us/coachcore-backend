package pro.shapeit.common.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class LocalIdUtils {
  public static String random() {
    return UUID.randomUUID().toString();
  }
}
