package pro.coachcore.training.plan.unit;

import java.time.DayOfWeek;
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
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.plan.TrainingPlan;
import pro.coachcore.training.plan.exercise.TrainingExercise;
import pro.coachcore.training.plan.parameter.ParameterDisplay;

@Data
@Entity(name = "training_unit")
@EntityListeners({ LocalIdEntityListener.class, AuditingEntityListener.class })
public class TrainingUnit implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

  @Column(name = "name")
  private String name;

  @Column(name = "notes")
  private String notes;

  @Enumerated(EnumType.STRING)
  @Column(name = "day_of_week")
  private DayOfWeek dayOfWeek;

  @Column(name = "index")
  private Integer index;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "parameter_display_id")
  private ParameterDisplay parameterDisplay;

  @OneToMany(mappedBy = "trainingUnit", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainingExercise> exercises = new ArrayList<>();
}
