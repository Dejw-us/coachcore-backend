package pro.coachcore.jpa.id;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;

/*
 * This class is used for creating local id in jpa entities.
 * Example usage: new LocalIdInitializer(this::setLocalId, this::getLocalId).initialize();
 */
@AllArgsConstructor
public class LocalIdInitializer {
  private Consumer<String> setter;
  private Supplier<String> getter;

  public void initialize() {
    if (getter.get() == null) {
      setter.accept(UUID.randomUUID().toString());
    }
  }
}
