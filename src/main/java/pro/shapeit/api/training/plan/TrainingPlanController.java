package pro.shapeit.api.training.plan;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingPlanMapper trainingPlanMapper;

  // --- GET ---

  @GetMapping
  ResponseEntity<List<TrainingPlanDto>> getTrainingPlans() {
    var plans = trainingPlanService.findAllTrainingPlans();
    var plansDto = plans.stream()
        .map(trainingPlanMapper::map)
        .toList();

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> getTrainingPlan(@PathVariable String planId) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var planDto = trainingPlanMapper.map(plan);

    return ResponseEntity
        .ok(planDto);
  }

  // --- POST ---

  @PostMapping
  ResponseEntity<TrainingPlanDto> postTrainingPlan(@RequestBody CreateTrainingPlanDto dto) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }

  // --- PATCH ---

  @PatchMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> patchTrainingPlan(@PathVariable String planId, @RequestBody UpdateTrainingPlanDto dto) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var updatedPlan = trainingPlanService.updateTrainingPlan(plan, dto);
    var updatedPlanDto = trainingPlanMapper.map(updatedPlan);

    return ResponseEntity
        .ok(updatedPlanDto);
  }

  // --- DELETE ---

  @DeleteMapping("/{planId}")
  ResponseEntity<MessageDto> deleteTrainingPlan(@PathVariable String planId) {
    var isDeleted = trainingPlanService.deleteTrainingPlanByLocalId(planId);

    if (isDeleted) {
      return ResponseEntity
          .ok(new MessageDto("Training plan has been deleted"));
    }
    return ResponseEntity
        .ok(new MessageDto("Failed to delete training plan. Training plan does not exist"));
  }
}
