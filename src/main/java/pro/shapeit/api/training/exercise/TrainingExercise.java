package pro.shapeit.api.training.exercise;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.exercise.catalog.CatalogExercise;
import pro.shapeit.api.training.set.TrainingSet;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TrainingExercise extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private CatalogExercise catalogExercise;

  private String notes;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "training_set_id"),
      joinColumns = @JoinColumn(name = "training_exercise_id")
  )
  private List<TrainingSet> sets;
}
