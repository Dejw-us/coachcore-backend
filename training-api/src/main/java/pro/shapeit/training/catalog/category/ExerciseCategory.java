package pro.shapeit.training.catalog.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.common.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ExerciseCategory extends BaseEntity {
  @Column(unique = true, nullable = false)
  private String name;

  private String description;
}
