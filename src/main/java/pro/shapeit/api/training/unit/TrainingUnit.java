package pro.shapeit.api.training.unit;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.training.exercise.TrainingExercise;
import pro.shapeit.api.training.plan.TrainingPlan;

import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
public class TrainingUnit {
  @Id
  @GeneratedValue
  private Long id;

  private String localId;

  private String notes;

  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  @OneToMany(mappedBy = "trainingUnit", fetch = FetchType.LAZY)
  private List<TrainingExercise> exercises;
}
