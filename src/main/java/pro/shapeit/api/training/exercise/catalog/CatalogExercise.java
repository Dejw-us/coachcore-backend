package pro.shapeit.api.training.exercise.catalog;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.exercise.category.ExerciseCategory;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CatalogExercise extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
