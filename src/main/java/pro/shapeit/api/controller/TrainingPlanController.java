package pro.shapeit.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.shapeit.api.model.TrainingPlan;

import java.util.List;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanController {
  @GetMapping("/demo")
  public ResponseEntity<?> getDemoTrainingPlans() {
    var plan1 = new TrainingPlan();

    return ResponseEntity.ok(List.of(plan1));
  }
}
