package pro.shapeit.backend.training.plan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.backend.common.entity.BaseEntity;
import pro.shapeit.backend.training.plan.goal.TrainingGoal;

import java.util.ArrayList;
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
  private List<TrainingGoal> goals = new ArrayList<>();
}
