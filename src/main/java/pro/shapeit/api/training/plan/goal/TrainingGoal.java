package pro.shapeit.api.training.plan.goal;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.shapeit.api.common.entity.IdentifiableEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TrainingGoal extends IdentifiableEntity {
  private String description;

  public TrainingGoal(String description) {
    this.description = description;
  }
}
