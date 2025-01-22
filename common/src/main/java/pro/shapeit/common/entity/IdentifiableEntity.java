package pro.shapeit.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import pro.shapeit.common.util.LocalIdUtils;

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
