package pro.shapeit.api.training.plan;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.api.training.plan.unit.exercise.TrainingExerciseMapper;
import pro.shapeit.api.training.plan.unit.exercise.set.TrainingSetMapper;

import java.util.List;

import static pro.shapeit.api.common.util.ControllerUtils.deleteResponse;

@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;

  private final TrainingPlanMapper trainingPlanMapper;
  private final TrainingExerciseMapper trainingExerciseMapper;
  private final TrainingSetMapper trainingSetMapper;

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

  // --- POST ---

  @PostMapping
  ResponseEntity<TrainingPlanDto> postTrainingPlan(
      @RequestBody @Valid CreateTrainingPlanDto dto
  ) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }

  // --- PATCH ---

  @PatchMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> patchTrainingPlan(
      @PathVariable String planId,
      @RequestBody @Valid UpdateTrainingPlanDto dto
  ) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var updatedPlan = trainingPlanService.updateTrainingPlan(plan, dto);
    var updatedPlanDto = trainingPlanMapper.map(updatedPlan);

    return ResponseEntity
        .ok(updatedPlanDto);
  }

  // --- DELETE ---

  @DeleteMapping("/{planId}")
  ResponseEntity<MessageDto> deleteTrainingPlan(
      @PathVariable String planId
  ) {
    var isDeleted = trainingPlanService.deleteTrainingPlanByLocalId(planId);

    return deleteResponse(isDeleted, "training plan");
  }
}
