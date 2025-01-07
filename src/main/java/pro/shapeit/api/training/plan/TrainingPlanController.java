package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
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

  @GetMapping("/{planLocalId}/goals")
  public ResponseEntity<?> getTrainingGoal(@PathVariable String planLocalId) {
    return ResponseEntity.ok(trainingPlanService.findTrainingGoals(planLocalId));
  }

  @GetMapping
  public ResponseEntity<?> getTrainingPlans() {
    return ResponseEntity.ok(trainingPlanService.findTrainingPlans(10));
  }

  @GetMapping("/{planLocalId}")
  public ResponseEntity<?> getTrainingPlan(@PathVariable String planLocalId) throws ResourceNotFoundException {
    return ResponseEntity.ok(trainingPlanService.findTrainingPlan(planLocalId));
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
    var unit = trainingUnitService.saveTrainingUnit(dto, plan);
    var unitDto = trainingUnitMapper.map(unit);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(unitDto);
  }

  @PostMapping
  public ResponseEntity<?> postTrainingPlan(@RequestBody CreateTrainingPlanDto dto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(trainingPlanService.saveTrainingPlan(dto));
  }
}
