package pro.shapeit.api.training.plan;

import jakarta.persistence.*;

@Entity
public class TrainingGoal {
  @Id
  @GeneratedValue
  private Long id;

  private String description;

  @ManyToOne
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  public String getDescription() {
    return description;
  }
}
