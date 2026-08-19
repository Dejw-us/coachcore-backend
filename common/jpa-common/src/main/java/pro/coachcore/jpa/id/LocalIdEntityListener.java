package pro.coachcore.jpa.id;

import jakarta.persistence.PrePersist;

public class LocalIdEntityListener {
  @PrePersist
  public void setupLocalId(IdentifiableEntity<String> entity) {
    new LocalIdInitializer(entity::setLocalId, entity::getLocalId).initialize();
  }
}
