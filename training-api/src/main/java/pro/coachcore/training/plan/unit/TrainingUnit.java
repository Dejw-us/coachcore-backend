package pro.coachcore.training.plan.unit;

import jakarta.persistence.*;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.plan.TrainingPlan;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;
}
