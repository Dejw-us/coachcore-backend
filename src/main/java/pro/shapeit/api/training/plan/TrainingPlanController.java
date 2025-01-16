package pro.shapeit.api.training.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.catalog.exercise.CatalogExercise;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.plan.unit.CreateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.TrainingUnitDto;
import pro.shapeit.api.training.plan.unit.TrainingUnitMapper;
import pro.shapeit.api.training.plan.unit.UpdateTrainingUnitDto;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseDto;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;

  private final TrainingPlanMapper trainingPlanMapper;
  private final TrainingUnitMapper trainingUnitMapper;
  private final TrainingExerciseMapper trainingExerciseMapper;

  // --- GET ---

  @GetMapping
  ResponseEntity<List<TrainingPlanDto>> getTrainingPlans() {
    var plans = trainingPlanService.findAllTrainingPlans();
    var plansDto = trainingPlanMapper.map(plans);

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> getTrainingPlan(
      @PathVariable String planId
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var planDto = trainingPlanMapper.map(plan);

    return ResponseEntity
        .ok(planDto);
  }

  @GetMapping("/{planId}/units")
  ResponseEntity<List<TrainingUnitDto>> getTrainingUnits(
      @PathVariable String planId
  ) throws ResourceNotFoundException {
    var units = trainingPlanService.findAllTrainingUnitsByTrainingPlanLocalId(planId);
    var unitsDto = trainingUnitMapper.map(units);

    return ResponseEntity
        .ok(unitsDto);
  }

  // --- POST ---

  @PostMapping
  ResponseEntity<TrainingPlanDto> postTrainingPlan(
      @RequestBody CreateTrainingPlanDto dto
  ) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }

  @PostMapping("/{planId}/units")
  ResponseEntity<TrainingUnitDto> postTrainingUnit(
      @PathVariable String planId,
      @RequestBody CreateTrainingUnitDto dto
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var savedUnit = trainingPlanService.saveTrainingUnit(plan, dto);
    var savedUnitDto = trainingUnitMapper.map(savedUnit);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedUnitDto);
  }

  @PostMapping("/{planId}/units/{unitId}/exercises")
  ResponseEntity<TrainingExerciseDto> postTrainingExercise(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestParam String catalogExerciseId
  ) throws ResourceNotFoundException {
    var unit = trainingPlanService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var catalogExercise = new CatalogExercise();
    var savedExercise = trainingPlanService.saveTrainingExercise(unit, catalogExercise);
    var savedExerciseDto = trainingExerciseMapper.map(savedExercise);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedExerciseDto);
  }

  // --- PATCH ---

  @PatchMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> patchTrainingPlan(
      @PathVariable String planId,
      @RequestBody UpdateTrainingPlanDto dto
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var updatedPlan = trainingPlanService.updateTrainingPlan(plan, dto);
    var updatedPlanDto = trainingPlanMapper.map(updatedPlan);

    return ResponseEntity
        .ok(updatedPlanDto);
  }

  @PostMapping("/{planId}/units/{unitId}")
  ResponseEntity<TrainingUnitDto> patchTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestBody UpdateTrainingUnitDto dto
  ) throws ResourceNotFoundException {
    var unit = trainingPlanService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var updatedUnit = trainingPlanService.updateTrainingUnit(unit, dto);
    var updatedUnitDto = trainingUnitMapper.map(updatedUnit);

    return ResponseEntity
        .ok(updatedUnitDto);
  }

  // --- DELETE ---

  @DeleteMapping("/{planId}")
  ResponseEntity<MessageDto> deleteTrainingPlan(
      @PathVariable String planId
  ) {
    var isDeleted = trainingPlanService.deleteTrainingPlanByLocalId(planId);

    if (isDeleted) {
      return ResponseEntity
          .ok(new MessageDto("Training plan has been deleted"));
    }
    return ResponseEntity
        .badRequest()
        .body(new MessageDto("Failed to delete training plan. Training plan does not exist"));
  }

  @DeleteMapping("/{planId}/units/{unitId}")
  ResponseEntity<MessageDto> deleteTrainingUnit(
      @PathVariable String planId,
      @PathVariable String unitId
  ) {
    var isDeleted = trainingPlanService.deleteTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);

    if (isDeleted) {
      return ResponseEntity
          .ok(new MessageDto("Training unit has been removed"));
    }
    return ResponseEntity
        .badRequest()
        .body(new MessageDto("Failed to delete training unit. Training unit does not exist"));
  }

  @DeleteMapping("/{planId}/exercises/{exerciseId}")
  ResponseEntity<MessageDto> deleteTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId
  ) {
    var isDeleted = trainingPlanService.deleteTrainingExerciseByLocalId(exerciseId);
  }
}
