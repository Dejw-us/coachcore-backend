package pro.shapeit.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.shapeit.api.service.TrainingPlanService;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;

  @Autowired
  public TrainingPlanController(TrainingPlanService trainingPlanService) {
    this.trainingPlanService = trainingPlanService;
  }

  @GetMapping("/demo")
  public ResponseEntity<?> getDemoTrainingPlans() {
    return ResponseEntity.ok(trainingPlanService.getDemoTrainingPlans());
  }
}
