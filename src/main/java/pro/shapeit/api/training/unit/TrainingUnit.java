package pro.shapeit.api.training.unit;

import jakarta.persistence.*;
import pro.shapeit.api.validation.annotation.ValidLocalId;
import pro.shapeit.api.training.exercise.TrainingExercise;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class TrainingUnit {
  @Id
  @GeneratedValue
  private Long id;

  @ValidLocalId
  private String localId;

  private String notes;

  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  @OneToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "training_unit_id"),
      inverseJoinColumns = @JoinColumn(name = "training_exercise_id")
  )
  private List<TrainingExercise> exercises;

  public String getLocalId() {
    return localId;
  }

  public String getNotes() {
    return notes;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public List<TrainingExercise> getExercises() {
    return exercises;
  }
}
