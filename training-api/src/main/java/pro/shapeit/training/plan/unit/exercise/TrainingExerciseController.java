package pro.shapeit.training.plan.unit.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pro.coachcore.dto.MessageDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.shapeit.training.catalog.exercise.CatalogExerciseService;
import pro.shapeit.training.plan.unit.TrainingUnitService;

@RestController
@RequestMapping("/v1/training-plans/{planId}")
@RequiredArgsConstructor
public class TrainingExerciseController {
  private final TrainingExerciseService trainingExerciseService;
  private final TrainingUnitService trainingUnitService;
  private final CatalogExerciseService catalogExerciseService;

  private final TrainingExerciseMapper trainingExerciseMapper;

  @PostMapping("/units/{unitId}/exercises")
  ResponseEntity<TrainingExerciseDto> postTrainingExercise(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestParam String catalogExerciseId
  ) throws ResourceNotFoundException {
    var unit = trainingUnitService.findTrainingUnitByTrainingPlanLocalIdAndLocalId(planId, unitId);
    var catalogExercise = catalogExerciseService.findCatalogExerciseByLocalId(catalogExerciseId);
    var savedExercise = trainingExerciseService.saveTrainingExercise(unit, catalogExercise);
    var savedExerciseDto = trainingExerciseMapper.map(savedExercise);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedExerciseDto);
  }

  @PatchMapping("/exercises/{exerciseId}")
  ResponseEntity<TrainingExerciseDto> patchTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId,
      @RequestBody UpdateTrainingExerciseDto dto,
      @RequestParam(required = false) String catalogExerciseId
  ) throws ResourceNotFoundException {
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var catalogExercise = catalogExerciseId == null ? null : catalogExerciseService.findCatalogExerciseByLocalId(catalogExerciseId);
    var updatedExercise = trainingExerciseService.updateTrainingExercise(exercise, catalogExercise, dto);
    var updatedExerciseDto = trainingExerciseMapper.map(updatedExercise);

    return ResponseEntity
        .ok(updatedExerciseDto);
  }

  @DeleteMapping("/exercises/{exerciseId}")
  ResponseEntity<MessageDto> deleteTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId
  ) {
    trainingExerciseService.deleteTrainingExerciseByLocalId(exerciseId);

    return ResponseEntity
        .ok(new MessageDto("Training exercise has been deleted"));
  }
}
