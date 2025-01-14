package pro.shapeit.api.training.plan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.plan.goal.TrainingGoal;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TrainingPlan extends BaseEntity {
  private String name;

  private String description;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "training_goal_id"),
      joinColumns = @JoinColumn(name = "training_plan_id")
  )
  private List<TrainingGoal> goals;
}
