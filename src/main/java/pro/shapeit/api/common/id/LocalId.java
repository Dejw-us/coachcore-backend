package pro.shapeit.api.common.id;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class LocalId {
  public static String random() {
    return UUID.randomUUID().toString();
  }
}
