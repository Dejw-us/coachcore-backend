package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/training-plans")
@RequiredArgsConstructor
public class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;

  @GetMapping("/demo")
  public ResponseEntity<?> getDemoTrainingPlans() {
    return ResponseEntity.ok(trainingPlanService.getDemoTrainingPlans());
  }

  @PostMapping
  public ResponseEntity<?> postTrainingPlan(@RequestBody CreateTrainingPlanDto dto) {
    return ResponseEntity.ok(dto.goals());
  }
}
