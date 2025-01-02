package pro.shapeit.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import pro.shapeit.api.constant.IntensityType;

//@Entity(name = "training_set")
public class TrainingSet {
  @Id
  @GeneratedValue
  private Long id;
  private String localId;
  private Integer reps;
  private Double intensity;
  private IntensityType intensityType;
  private String rate;
  private Double rest;
}
