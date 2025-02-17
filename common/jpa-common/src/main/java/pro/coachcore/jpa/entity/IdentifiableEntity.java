package pro.shapeit.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import pro.shapeit.util.LocalIdUtils;

@Getter
@MappedSuperclass
public abstract class IdentifiableEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private String localId;

  @PrePersist
  private void setupLocalId() {
    if (localId == null) {
      localId = LocalIdUtils.random();
    }
  }
}
