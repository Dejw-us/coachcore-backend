package pro.coachcore.training.plan.rating;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rating/{planId}")
public class TrainingPlanRatingController {
  private final TrainingPlanRatingService trainingPlanRatingService;
  private final TrainingPlanRatingMapper trainingPlanRatingMapper;

  @PutMapping
  ResponseEntity<TrainingPlanRatingDto> putRating(
      @PathVariable String planId,
      @RequestBody UpdateTrainingPlanRatingDto body) {
    var updatedRating = trainingPlanRatingService.setRating(planId, body.stars());
    return ResponseEntity.ok(trainingPlanRatingMapper.map(updatedRating));
  }

  @GetMapping("/average")
  ResponseEntity<TrainingPlanRatingDto> getAverageRating(@PathVariable String planId) {
    var averageRating = trainingPlanRatingService.getAveragePlanRating(planId);
    return ResponseEntity.ok(new TrainingPlanRatingDto(averageRating));
  }
}
