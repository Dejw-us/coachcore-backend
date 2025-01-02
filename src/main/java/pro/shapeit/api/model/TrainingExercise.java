package pro.shapeit.api.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.annotation.validation.ValidLocalId;

import java.util.List;

@Entity
@Data
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
}
