package pro.shapeit.api.training.exercise.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ExerciseCategory extends BaseEntity {
  @Column(unique = true, nullable = false)
  private String name;

  private String description;
}
