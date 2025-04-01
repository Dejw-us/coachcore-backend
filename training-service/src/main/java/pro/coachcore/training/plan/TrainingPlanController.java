package pro.coachcore.training.plan;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.tag.AddTagsDto;
import pro.coachcore.training.plan.tag.RemoveTagDto;

@Slf4j
@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final TrainingPlanMapper trainingPlanMapper;

  @GetMapping("/me")
  public ResponseEntity<List<TrainingPlanDto>> getUserTrainingPlans() {
    var plans = trainingPlanMapper.map(trainingPlanService.getUserPlans());

    return ResponseEntity.ok(plans);
  }

  @PostMapping
  ResponseEntity<TrainingPlanDto> postTrainingPlan(@RequestBody @Valid CreateTrainingPlanDto dto) {
    var savedPlan = trainingPlanService.savePlan(dto);
    var savedPlanDto = trainingPlanMapper.map(savedPlan);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedPlanDto);
  }

  @PatchMapping("/{planId}")
  ResponseEntity<TrainingPlanDto> patchTrainingPlan(@PathVariable String planId,
      @RequestBody @Valid UpdateTrainingPlanDto dto) throws ResourceNotFoundException {
    var plan = trainingPlanService.getPlan(planId);
    var updatedPlan = trainingPlanService.updatePlan(plan, dto);
    var updatedPlanDto = trainingPlanMapper.map(updatedPlan);

    return ResponseEntity.ok(updatedPlanDto);
  }

  @DeleteMapping("/{planId}")
  ResponseEntity<DeletedObjectDto> deleteTrainingPlan(@PathVariable String planId) {
    var deletedPlan = trainingPlanService.deletePlan(planId);

    return ResponseEntity.ok(deletedPlan);
  }

  @PutMapping("/{planId}/tags")
  ResponseEntity<TrainingPlanDto> putTags(
      @PathVariable String planId,
      @RequestBody AddTagsDto body) {
    var plan = trainingPlanService.getPlan(planId);
    var updatedPlan = trainingPlanService.addTag(plan, body.tags());

    return ResponseEntity.ok(trainingPlanMapper.map(updatedPlan));
  }

  @DeleteMapping("/{planId}/tags")
  ResponseEntity<TrainingPlanDto> deleteTag(
      @PathVariable String planId,
      @RequestBody RemoveTagDto body) {
    var plan = trainingPlanService.getPlan(planId);
    var updatedPlan = trainingPlanService.removeTag(plan, body.tag());

    return ResponseEntity.ok(trainingPlanMapper.map(updatedPlan));
  }
}
