package pro.coachcore.training.plan.rating;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.GlobalHandlerRuntimeException;
import pro.coachcore.training.plan.TrainingPlanService;

@Service
@RequiredArgsConstructor
public class TrainingPlanRatingService {
  private final TrainingPlanRatingRepository trainingPlanRatingRepository;
  private final TrainingPlanService trainingPlanService;
  private final AuditorAware<String> auditorAware;

  public Integer getAveragePlanRating(String planLocalId) {
    return trainingPlanRatingRepository.getAverageRating(planLocalId).intValue();
  }

  public TrainingPlanRating setRating(String planLocalId, Integer stars) {
    var userId = auditorAware.getCurrentAuditor().orElseThrow(GlobalHandlerRuntimeException.supplier(
        "You have to be logged in to rate plans",
        HttpStatus.UNAUTHORIZED,
        "NOT_AUTHORIZED"));
    var rating = trainingPlanRatingRepository.findByUserId(userId)
        .orElseGet(() -> createRating(planLocalId, stars));
    rating.setStars(stars);
    return trainingPlanRatingRepository.save(rating);
  }

  public TrainingPlanRating createRating(String planLocalId, Integer stars) {
    var newRating = TrainingPlanRating.withStars(stars);
    var plan = trainingPlanService.getPlan(planLocalId);
    newRating.setTrainingPlan(plan);
    return newRating;
  }
}
