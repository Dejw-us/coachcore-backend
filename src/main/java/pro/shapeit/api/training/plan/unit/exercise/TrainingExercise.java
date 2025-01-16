package pro.shapeit.api.training.plan.unit.exercise;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.catalog.exercise.CatalogExercise;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSet;

import java.util.ArrayList;
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
  private List<TrainingSet> sets = new ArrayList<>();
}
