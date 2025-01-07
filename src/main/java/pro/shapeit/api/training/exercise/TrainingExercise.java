package pro.shapeit.api.training.exercise;

import jakarta.persistence.*;
import lombok.Data;
import pro.shapeit.api.training.exercise.catalog.CatalogExercise;
import pro.shapeit.api.training.set.TrainingSet;
import pro.shapeit.api.training.unit.TrainingUnit;

import java.util.List;

@Entity
@Data
public class TrainingExercise {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 36, unique = true)
  private String localId;

  @ManyToOne(fetch = FetchType.LAZY)
  private CatalogExercise catalogExercise;

  private String notes;

  @OneToMany(mappedBy = "trainingExercise")
  private List<TrainingSet> sets;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_unit_id")
  private TrainingUnit trainingUnit;
}
