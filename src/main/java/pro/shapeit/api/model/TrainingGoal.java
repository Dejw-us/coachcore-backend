package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TrainingGoal {
  @Id
  @GeneratedValue
  private Long id;

  private String description;

  @ManyToOne
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;
}
