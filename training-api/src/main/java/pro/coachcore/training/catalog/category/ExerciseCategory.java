package pro.coachcore.training.catalog.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import pro.coachcore.jpa.id.LocalIdInitializer;

@Data
@Entity
public class ExerciseCategory {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

  @Column(unique = true, nullable = false)
  private String name;

  private String description;

  @PrePersist
  private void setupLocalId() {
    new LocalIdInitializer(this::setLocalId, this::getLocalId).initialize();
  }
}
