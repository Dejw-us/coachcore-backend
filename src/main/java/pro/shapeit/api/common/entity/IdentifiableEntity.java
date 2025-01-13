package pro.shapeit.api.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import pro.shapeit.api.common.id.LocalId;

@Getter
@MappedSuperclass
public abstract class IdentifiableEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String localId;

  @PrePersist
  private void setupLocalId() {
    if (localId == null) {
      localId = LocalId.random();
    }
  }
}
