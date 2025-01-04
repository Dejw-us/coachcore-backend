package pro.shapeit.api.training.plan;

import jakarta.persistence.*;
import pro.shapeit.api.validation.annotation.ValidLocalId;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@Entity
public class TrainingPlan {
  @Id
  @GeneratedValue
  private Long id;

  @ValidLocalId
  private String localId;

  private String description;

  @OneToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "training_plan_id"),
      inverseJoinColumns = @JoinColumn(name = "training_unit_id")
  )
  private List<TrainingUnit> units;

  @OneToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "training_plan_id"),
      inverseJoinColumns = @JoinColumn(name = "training_goal_id")
  )
  private List<TrainingGoal> goals;

  public String getLocalId() {
    return localId;
  }

  public String getDescription() {
    return description;
  }

  public List<TrainingUnit> getUnits() {
    return units;
  }

  public List<TrainingGoal> getGoals() {
    return goals;
  }
}
