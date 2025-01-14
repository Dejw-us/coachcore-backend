package pro.shapeit.api.training.unit;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.exercise.TrainingExercise;
import pro.shapeit.api.training.plan.TrainingPlan;

import java.time.DayOfWeek;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TrainingUnit extends BaseEntity {
  private String name;

  private String notes;

  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "training_exercise_id"),
      joinColumns = @JoinColumn(name = "training_unit_id")
  )
  private List<TrainingExercise> exercises;
}
