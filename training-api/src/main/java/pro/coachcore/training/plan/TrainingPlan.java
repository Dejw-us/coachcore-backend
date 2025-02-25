package pro.coachcore.training.plan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.coachcore.training.plan.goal.TrainingGoal;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TrainingPlan {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

  private String name;

  private String description;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "training_goal_id"),
      joinColumns = @JoinColumn(name = "training_plan_id")
  )
  private List<TrainingGoal> goals = new ArrayList<>();
}
