package pro.shapeit.api.training.set;

import jakarta.persistence.*;
import pro.shapeit.api.validation.annotation.ValidLocalId;

@Entity
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

  public enum IntensityType {
    RIR,
    RPE
  }
}
