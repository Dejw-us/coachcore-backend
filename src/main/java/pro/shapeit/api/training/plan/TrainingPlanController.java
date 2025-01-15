package pro.shapeit.api.training.plan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingPlanMapper trainingPlanMapper;

  @GetMapping
  ResponseEntity<?> getTrainingPlans() {
    var plans = trainingPlanService.findAll();
    var plansDto = plans.stream()
        .map(trainingPlanMapper::map)
        .toList();

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planId}")
  ResponseEntity<?> getTrainingPlan(@PathVariable String planId) throws ResourceNotFoundException {
    var plan = trainingPlanService.findByLocalId(planId);
    var planDto = trainingPlanMapper.map(plan);

    return ResponseEntity
        .ok(planDto);
  }
}
