package pro.coachcore.training.catalog.exercise;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.coachcore.jpa.entity.BaseEntity;
import pro.coachcore.training.catalog.category.ExerciseCategory;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CatalogExercise extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
