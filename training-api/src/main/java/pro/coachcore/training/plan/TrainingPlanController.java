package pro.coachcore.training.plan;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.dto.MessageDto;
import pro.coachcore.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingPlanMapper trainingPlanMapper;

  @GetMapping("/me")
  public ResponseEntity<List<TrainingPlanDto>> getUserTrainingPlans(
      @AuthenticationPrincipal Jwt jwt) {
    var plans = trainingPlanMapper.map(trainingPlanService.findAllUserTrainingPlans(jwt));

    return ResponseEntity
        .ok(plans);
  }

  @GetMapping
  ResponseEntity<List<TrainingPlanDto>> getTrainingPlans() {
    var plans = trainingPlanService.findAllTrainingPlans();
    var plansDto = trainingPlanMapper.map(plans);

    return ResponseEntity
        .ok(plansDto);
  }

  @GetMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> getTrainingPlan(
      @PathVariable String planId) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var planDto = trainingPlanMapper.map(plan);

    log.debug("get plan: {}", plan);

    return ResponseEntity
        .ok(planDto);
  }

  @PostMapping
  ResponseEntity<TrainingPlanDto> postTrainingPlan(
      @RequestBody @Valid CreateTrainingPlanDto dto) {
    var savedPlan = trainingPlanService.saveTrainingPlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedPlanDto);
  }

  @PatchMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> patchTrainingPlan(
      @PathVariable String planId,
      @RequestBody @Valid UpdateTrainingPlanDto dto) throws ResourceNotFoundException {
    var plan = trainingPlanService.findTrainingPlanByLocalId(planId);
    var updatedPlan = trainingPlanService.updateTrainingPlan(plan, dto);
    var updatedPlanDto = trainingPlanMapper.map(updatedPlan);

    return ResponseEntity
        .ok(updatedPlanDto);
  }

  @DeleteMapping("/{planId}")
  ResponseEntity<DeletedObjectDto> deleteTrainingPlan(
      @PathVariable String planId) {
    trainingPlanService.deleteTrainingPlanByLocalId(planId);

    return ResponseEntity
        .ok(new DeletedObjectDto(planId));
  }
}
