package pro.shapeit.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pro.shapeit.api.annotation.validation.ValidLocalId;

@Entity
public class TrainingExercise {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36)
  @ValidLocalId
  private String localId;

  private String exerciseName;

  private String notes;

//  private List<TrainingSet> sets;
}
