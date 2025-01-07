package pro.shapeit.api.training.set;

import jakarta.persistence.*;
import pro.shapeit.api.training.exercise.TrainingExercise;

@Entity
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_exercise_id")
  private TrainingExercise trainingExercise;

  public enum IntensityType {
    RIR,
    RPE
  }
}
