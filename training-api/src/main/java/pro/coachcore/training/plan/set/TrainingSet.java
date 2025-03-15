package pro.coachcore.training.plan.set;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.plan.exercise.TrainingExercise;

@Data
@Entity(name = "training_set")
@EntityListeners({ LocalIdEntityListener.class, AuditingEntityListener.class })
public class TrainingSet implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_exercise_id")
  private TrainingExercise trainingExercise;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

  @Column(name = "reps")
  private Integer reps;

  @Column(name = "rest_seconds")
  private Double restSeconds;

  @Column(name = "intensity")
  private Double intensity;

  @Column(name = "rate")
  private String rate;

  @Column(name = "weight")
  private Double weight;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "index")
  private Long index;
}
