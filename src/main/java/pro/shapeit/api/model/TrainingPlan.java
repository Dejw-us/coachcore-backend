package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.annotation.validation.ValidLocalId;

import java.util.List;

@Entity
@Data
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
}
