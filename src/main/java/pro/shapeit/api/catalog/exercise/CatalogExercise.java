package pro.shapeit.api.catalog.exercise;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.catalog.category.ExerciseCategory;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CatalogExercise extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
