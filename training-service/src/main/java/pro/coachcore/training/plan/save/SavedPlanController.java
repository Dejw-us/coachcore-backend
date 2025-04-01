package pro.coachcore.training.plan.save;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.coachcore.training.plan.TrainingPlanService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/saved-plans")
public class SavedPlanController {
  private final TrainingPlanService trainingPlanService;
  private final SavedPlanService savedPlanService;
  private final SavedPlanMapper savedPlanMapper;

  @PostMapping
  ResponseEntity<SavedPlanDto> savePlan(
      @RequestParam String planId) {
    var plan = trainingPlanService.getPlan(planId);
    var savedPlan = savedPlanService.savePlan(plan);

    return ResponseEntity.ok(savedPlanMapper.map(savedPlan));
  }

  @GetMapping
  ResponseEntity<List<SavedPlanDto>> getSavedPlans() {
    var savedPlans = savedPlanService.getSavedPlans();
    return ResponseEntity.ok(savedPlanMapper.map(savedPlans));
  }
}
