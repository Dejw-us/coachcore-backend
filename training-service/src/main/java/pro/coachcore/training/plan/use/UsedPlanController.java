package pro.coachcore.training.plan.use;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.coachcore.training.plan.TrainingPlanDto;
import pro.coachcore.training.plan.TrainingPlanMapper;
import pro.coachcore.training.plan.TrainingPlanService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/used-plans")
public class UsedPlanController {
  private final TrainingPlanService trainingPlanService;
  private final UsedPlanService usedPlanService;
  private final UsedPlanMapper usedPlanMapper;
  private final TrainingPlanMapper trainingPlanMapper;

  @PostMapping
  ResponseEntity<UsedPlanDto> postUsedPlans(
      @RequestParam String planId) {
    var plan = trainingPlanService.getPlan(planId);
    var usedPlan = usedPlanService.savePlan(plan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(usedPlanMapper.map(usedPlan));
  }

  @GetMapping
  ResponseEntity<List<TrainingPlanDto>> getUsedPlans(
      @RequestParam Integer page,
      @RequestParam Integer size) {
    var usedPlans = usedPlanService.getPlans(PageRequest.of(page, size)).getContent();

    return ResponseEntity.ok(trainingPlanMapper.map(usedPlans));
  }
}
