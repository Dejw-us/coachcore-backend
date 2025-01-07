package pro.shapeit.api.training.goal;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.training.plan.TrainingPlan;

import java.util.UUID;

@Entity
@Data
public class TrainingGoal {
  @Id
  @GeneratedValue
  private Long id;

  private String localId;

  private String description;

  @ManyToOne
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  public TrainingGoal(String description) {
    this.description = description;
    localId = UUID.randomUUID().toString();
  }
}
