package pro.shapeit.api.training.plan;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.training.goal.TrainingGoal;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@Entity
@Data
public class TrainingPlan {
  @Id
  @GeneratedValue
  private Long id;

  private String localId;

  private String name;

  private String description;

  @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY)
  private List<TrainingUnit> units;

  @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY)
  private List<TrainingGoal> goals;
}
