package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.annotation.validation.ValidLocalId;
import pro.shapeit.api.constant.IntensityType;

@Entity
@Data
public class TrainingSet {
  @Id
  @GeneratedValue
  private Long id;

  @ValidLocalId
  private String localId;

  private Integer reps;

  private Double intensity;

  private IntensityType intensityType;

  private String rate;

  private Double rest;
}
