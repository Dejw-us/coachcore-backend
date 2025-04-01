package pro.coachcore.training.plan;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.catalog.category.ExerciseCategoryDto;
import pro.coachcore.training.catalog.category.ExerciseCategoryMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/training-plans")
public class PublicTrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingPlanMapper trainingPlanMapper;
  private final ExerciseCategoryMapper exerciseCategoryMapper;

  @GetMapping
  ResponseEntity<List<TrainingPlanDto>> getTrainingPlans() {
    var plans = trainingPlanService.getAllPlans();
    var plansDto = trainingPlanMapper.map(plans);

    return ResponseEntity.ok(plansDto);
  }

  @GetMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> getTrainingPlan(@PathVariable String planId)
      throws ResourceNotFoundException {
    var plan = trainingPlanService.getPlan(planId);
    var planDto = trainingPlanMapper.map(plan);
    return ResponseEntity.ok(planDto);
  }

  @GetMapping("/{planId}/category")
  ResponseEntity<ExerciseCategoryDto> getPlanCategory(@PathVariable String planId) {
    var category = trainingPlanService.getDominantCategory(planId);
    return ResponseEntity.ok(exerciseCategoryMapper.map(category));
  }

  @GetMapping("/{planId}/tr")
  ResponseEntity<TR> getPlanTR(
      @PathVariable String planId) {
    var tr = trainingPlanService.getTR(planId);
    return ResponseEntity.ok(tr);
  }
}
