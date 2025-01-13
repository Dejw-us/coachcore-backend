package pro.shapeit.api.training.plan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.goal.TrainingGoal;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TrainingPlan extends BaseEntity {
  private String name;

  private String description;

  @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY)
  private List<TrainingUnit> units;

  @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY)
  private List<TrainingGoal> goals;
}
