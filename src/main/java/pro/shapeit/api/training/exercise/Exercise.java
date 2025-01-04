package pro.shapeit.api.training.exercise;

import jakarta.persistence.*;
import pro.shapeit.api.validation.annotation.ValidLocalId;

@Entity
public class Exercise {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36, unique = true)
  @ValidLocalId
  private String localId;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExerciseCategory category;
}
