package pro.coachcore.training.plan.exercise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.catalog.exercise.CatalogExercise;
import pro.coachcore.training.plan.set.TrainingSet;
import pro.coachcore.training.plan.unit.TrainingUnit;

@Data
@Entity(name = "training_exercise")
@EntityListeners({LocalIdEntityListener.class, AuditingEntityListener.class})
public class TrainingExercise implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "index")
  private Long index;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

  @Column(name = "intensity_type")
  @Enumerated(value = EnumType.STRING)
  private IntensityType intensityType = IntensityType.RPE;

  @Column(name = "weight_type")
  @Enumerated(value = EnumType.STRING)
  private WeightType weightType = WeightType.KG;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "catalog_exercise_id")
  private CatalogExercise catalogExercise;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_unit_id")
  private TrainingUnit trainingUnit;

  @Column(name = "notes")
  private String notes;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingExercise", cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<TrainingSet> trainingSets = new ArrayList<>();

  public enum IntensityType {
    RPE, RIR
  }

  public enum WeightType {
    KG, LBS
  }
}
