package pro.coachcore.training.plan.set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TrainingSet {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String localId;

  private Integer reps;

  private Double restSeconds;

  private Double intensity;

  private IntensityType intensityType;

  private String rate;

  private Double weight;

  private WeightType weightType;

  public enum WeightType {
    KG,
    LBS
  }

  public enum IntensityType {
    RIR,
    RPE
  }
}
