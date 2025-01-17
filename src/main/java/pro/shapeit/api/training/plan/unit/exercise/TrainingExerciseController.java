package pro.shapeit.api.training.plan.unit.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.catalog.exercise.CatalogExercise;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.common.exception.resource.ResourceNotFoundException;
import pro.shapeit.api.training.plan.unit.TrainingUnitService;

import static pro.shapeit.api.common.util.ControllerUtils.deleteResponse;

@RestController
@RequestMapping("/v1/training-plans/{planId}")
@RequiredArgsConstructor
public class TrainingExerciseController {
  private final TrainingExerciseService trainingExerciseService;
  private final TrainingUnitService trainingUnitService;

  private final TrainingExerciseMapper trainingExerciseMapper;

  // --- POST ---

  @PostMapping("/units/{unitId}/exercises")
  ResponseEntity<TrainingExerciseDto> postTrainingExercise(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestParam String catalogExerciseId
  ) throws ResourceNotFoundException {
    var unit = trainingUnitService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var catalogExercise = new CatalogExercise(); // TODO handle fetching catalog exercise
    var savedExercise = trainingExerciseService.saveTrainingExercise(unit, catalogExercise);
    var savedExerciseDto = trainingExerciseMapper.map(savedExercise);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedExerciseDto);
  }

  // --- PATCH ---

  @PatchMapping("/exercises/{exerciseId}")
  ResponseEntity<TrainingExerciseDto> patchTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId,
      @RequestBody UpdateTrainingExerciseDto dto,
      @RequestParam(required = false) String catalogExerciseId
  ) throws ResourceNotFoundException {
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var catalogExercise = new CatalogExercise(); // TODO handle fetching catalog exercise
    var updatedExercise = trainingExerciseService.updateTrainingExercise(exercise, catalogExercise, dto);
    var updatedExerciseDto = trainingExerciseMapper.map(updatedExercise);

    return ResponseEntity
        .ok(updatedExerciseDto);
  }

  // --- DELETE ---

  @DeleteMapping("/exercises/{exerciseId}")
  ResponseEntity<MessageDto> deleteTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId
  ) {
    var isDeleted = trainingExerciseService.deleteTrainingExerciseByLocalId(exerciseId);

    return deleteResponse(isDeleted, "training exercise");
  }
}
