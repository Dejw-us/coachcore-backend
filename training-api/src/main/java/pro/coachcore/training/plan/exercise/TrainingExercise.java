package pro.coachcore.training.plan.exercise;

import jakarta.persistence.*;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.catalog.exercise.CatalogExercise;
import pro.coachcore.training.plan.unit.TrainingUnit;

import java.time.LocalDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity(name = "training_exercise")
@EntityListeners({ LocalIdEntityListener.class, AuditingEntityListener.class })
public class TrainingExercise implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

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
}
