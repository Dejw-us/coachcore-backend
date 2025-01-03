package pro.shapeit.api.model;

import jakarta.persistence.*;
import pro.shapeit.api.annotation.validation.ValidLocalId;

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
