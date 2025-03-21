package pro.coachcore.training.plan;

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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.Data;
import pro.coachcore.jpa.id.IdentifiableEntity;
import pro.coachcore.jpa.id.LocalIdEntityListener;
import pro.coachcore.training.plan.goal.TrainingGoal;
import pro.coachcore.training.plan.unit.TrainingUnit;

@Data
@Entity(name = "training_plan")
@EntityListeners({LocalIdEntityListener.class, AuditingEntityListener.class})
public class TrainingPlan implements IdentifiableEntity<String> {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, name = "local_id")
  private String localId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "weeks")
  private Integer weeks;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "training_goal_id"),
      joinColumns = @JoinColumn(name = "training_plan_id"))
  private List<TrainingGoal> goals = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingPlan", cascade = CascadeType.ALL)
  private List<TrainingUnit> units = new ArrayList<>();
}
