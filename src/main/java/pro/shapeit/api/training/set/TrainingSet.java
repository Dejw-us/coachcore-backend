package pro.shapeit.api.training.set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.shapeit.api.common.entity.BaseEntity;
import pro.shapeit.api.training.exercise.TrainingExercise;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TrainingSet extends BaseEntity {
  private Integer reps;

  private Double rest;

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
