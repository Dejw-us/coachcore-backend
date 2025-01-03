package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.annotation.validation.ValidLocalId;

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
