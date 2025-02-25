package pro.coachcore.training.plan.goal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class TrainingGoal {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;
  
  private String description;

  public TrainingGoal(String description) {
    this.description = description;
  }
}
