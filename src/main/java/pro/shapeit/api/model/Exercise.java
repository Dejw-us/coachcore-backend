package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.annotation.validation.ValidLocalId;

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

  public String getLocalId() {
    return localId;
  }

  public String getName() {
    return name;
  }

  public ExerciseCategory getCategory() {
    return category;
  }
}
