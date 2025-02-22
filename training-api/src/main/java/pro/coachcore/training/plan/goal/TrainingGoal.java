package pro.coachcore.training.plan.goal;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pro.coachcore.jpa.entity.IdentifiableEntity;

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
