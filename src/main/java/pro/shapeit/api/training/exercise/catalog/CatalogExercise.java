package pro.shapeit.api.training.exercise.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.shapeit.api.training.exercise.category.ExerciseCategory;

@Entity
@Data
public class CatalogExercise {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36, unique = true)
  private String localId;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
