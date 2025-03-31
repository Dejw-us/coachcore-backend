package pro.coachcore.training.plan.rating;

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
import lombok.Data;
import pro.coachcore.training.plan.TrainingPlan;

@Data
@Entity(name = "training_plan_rating")
@EntityListeners(AuditingEntityListener.class)
public class TrainingPlanRating {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedBy
  @Column(name = "user_id", nullable = false, updatable = false)
  private String userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_plan_id")
  private TrainingPlan trainingPlan;

  @Column(name = "stars")
  private Integer stars;

  public static TrainingPlanRating withStars(Integer stars) {
    if (stars < 0 || stars > 5) {
      throw new IllegalArgumentException("Stars must be betwen 0 and 5");
    }
    var rating = new TrainingPlanRating();
    rating.setStars(stars);
    return rating;
  }
}
