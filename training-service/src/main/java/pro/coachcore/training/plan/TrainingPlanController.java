package pro.coachcore.training.plan;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.save.SavedPlanService;
import pro.coachcore.training.plan.tag.AddTagsDto;
import pro.coachcore.training.plan.tag.RemoveTagDto;
import pro.coachcore.training.plan.use.UsedPlanService;

@Slf4j
@RestController
@RequestMapping("/v1/training-plans")
@RequiredArgsConstructor
class TrainingPlanController {
  private final TrainingPlanService trainingPlanService;
  private final UsedPlanService usedPlanService;
  private final SavedPlanService savedPlanService;
  private final TrainingPlanMapper trainingPlanMapper;

  @GetMapping("/me")
  public ResponseEntity<List<TrainingPlanDto>> getUserTrainingPlans(
      @RequestParam(defaultValue = "false") Boolean used,
      @RequestParam(defaultValue = "false") Boolean saved,
      @RequestParam(defaultValue = "false") Boolean my,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {
    var pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
    var plans = new HashSet<TrainingPlan>();

    if (used) {
      plans.addAll(usedPlanService.getPlans(pageable).getContent());
    }
    if (saved) {
      plans.addAll(savedPlanService.getPlans(pageable).getContent());
    }
    if (my) {
      plans.addAll(trainingPlanService.getUserPlans(pageable).getContent());
    }

    if (plans.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    var uniquePlans = plans.stream()
        .collect(Collectors.toMap(TrainingPlan::getId, plan -> plan, (existing, replacement) -> existing))
        .values();

    return ResponseEntity.ok(trainingPlanMapper.map(uniquePlans));
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
