package pro.coachcore.training.plan.rating;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanRatingRepository extends JpaRepository<TrainingPlanRating, Long> {
  @Query("SELECT COALESCE(AVG(r.stars), 0) FROM training_plan_rating r where r.trainingPlan.localId = :trainingPlanLocalId")
  Double getAverageRating(@Param("trainingPlanLocalId") String planLocalId);

  Optional<TrainingPlanRating> findByUserIdAndTrainingPlan_LocalId(String userId, String planLocalId);
}
