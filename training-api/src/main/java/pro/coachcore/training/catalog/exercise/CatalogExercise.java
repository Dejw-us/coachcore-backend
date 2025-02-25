package pro.coachcore.training.catalog.exercise;

import jakarta.persistence.*;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.training.catalog.category.ExerciseCategory;

@Data
@Entity
public class CatalogExercise implements IdentifiableEntity<String> {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
