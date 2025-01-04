package pro.shapeit.api.training.exercise;

import jakarta.persistence.*;
import pro.shapeit.api.validation.annotation.ValidLocalId;
import pro.shapeit.api.training.set.TrainingSet;

import java.util.List;

@Entity
public class TrainingExercise {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36, unique = true)
  @ValidLocalId
  private String localId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Exercise exercise;

  private String notes;

  @OneToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "training_exercise_id"),
      inverseJoinColumns = @JoinColumn(name = "training_set_id")
  )
  private List<TrainingSet> sets;

  public String getLocalId() {
    return localId;
  }

  public Exercise getExercise() {
    return exercise;
  }

  public String getNotes() {
    return notes;
  }

  public List<TrainingSet> getSets() {
    return sets;
  }
}
