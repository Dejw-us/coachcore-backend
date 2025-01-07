package pro.shapeit.api.training.exercise.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class ExerciseCategory {
  @Id
  @GeneratedValue
  private Long id;

  private String localId;

  @Column(unique = true, nullable = false)
  private String name;
}
