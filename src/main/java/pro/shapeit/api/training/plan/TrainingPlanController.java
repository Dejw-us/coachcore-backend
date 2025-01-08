package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.goal.TrainingGoalMapper;
import pro.shapeit.api.training.unit.CreateTrainingUnitDto;
import pro.shapeit.api.training.unit.TrainingUnitMapper;
import pro.shapeit.api.training.unit.TrainingUnitService;

@RestController
@RequestMapping("/training-plans")
@RequiredArgsConstructor
public class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingUnitService trainingUnitService;

  private final TrainingUnitMapper trainingUnitMapper;
  private final TrainingPlanMapper trainingPlanMapper;
  private final TrainingGoalMapper trainingGoalMapper;

  @GetMapping("/{planLocalId}/goals")
  public ResponseEntity<?> getTrainingGoal(@PathVariable String planLocalId) {
    var goals = trainingPlanService.findTrainingGoals(planLocalId);
    var goalsDto = goals.stream()
        .map(trainingGoalMapper::map)
        .toList();

    return ResponseEntity
        .ok(goalsDto);
  }

  @GetMapping
  public ResponseEntity<?> getTrainingPlans() {
    var plans = trainingPlanService.findTrainingPlans(10);
    var plansDto = plans
        .map(trainingPlanMapper::map)
        .toList();

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planLocalId}")
  public ResponseEntity<?> getTrainingPlan(@PathVariable String planLocalId) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlan(planLocalId);
    var planDto = trainingPlanMapper.map(plan);

    return ResponseEntity
        .ok(planDto);
  }

  @GetMapping("/{planLocalId}/units")
  public ResponseEntity<?> getTrainingPlanUnits(@PathVariable String planLocalId) throws ResourceNotFoundException {
    var units = trainingUnitService.findTrainingUnits(planLocalId);
    var unitsDto = units.stream()
        .map(trainingUnitMapper::map)
        .toList();

    return ResponseEntity.ok(unitsDto);
  }

  @PostMapping("/{planLocalId}/units")
  public ResponseEntity<?> postTrainingPlanUnit(
      @PathVariable String planLocalId,
      @RequestBody CreateTrainingUnitDto dto
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlan(planLocalId);
    var savedUnit = trainingUnitService.saveTrainingUnit(dto, plan);
    var savedUnitDto = trainingUnitMapper.map(savedUnit);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedUnitDto);
  }

  @PostMapping
  public ResponseEntity<?> postTrainingPlan(@RequestBody CreateTrainingPlanDto dto) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }
}
