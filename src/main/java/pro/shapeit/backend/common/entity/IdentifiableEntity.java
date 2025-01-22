package pro.shapeit.backend.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import pro.shapeit.backend.common.util.LocalIdUtils;

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
      localId = LocalIdUtils.random();
    }
  }
}
