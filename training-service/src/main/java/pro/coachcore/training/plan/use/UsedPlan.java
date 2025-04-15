package pro.coachcore.training.plan.use;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.coachcore.training.plan.TrainingPlan;

@Data
@Entity
@NoArgsConstructor
@Table(name = "used_plan")
@EntityListeners({ AuditingEntityListener.class })
public class UsedPlan {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedBy
  @Column(name = "user_id")
  private String userId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan usedPlan;

  public UsedPlan(TrainingPlan usedPlan) {
    this.usedPlan = usedPlan;
  }
}
