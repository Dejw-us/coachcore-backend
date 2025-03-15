package pro.coachcore.training.plan.exercise;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.dto.MessageDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.catalog.exercise.CatalogExerciseService;
import pro.coachcore.training.plan.unit.TrainingUnitService;

@Slf4j
@RestController
@RequestMapping("/v1/training-plans/{planId}")
@RequiredArgsConstructor
public class TrainingExerciseController {
  private final TrainingExerciseService trainingExerciseService;
  private final TrainingUnitService trainingUnitService;
  private final CatalogExerciseService catalogExerciseService;

  private final TrainingExerciseMapper trainingExerciseMapper;

  @GetMapping("/units/{unitId}/exercises")
  ResponseEntity<List<TrainingExerciseDto>> getTrainingExercises(
      @PathVariable String unitId) {
    var exercises = trainingExerciseService.findAllByUnitLocalId(unitId);

    return ResponseEntity
        .ok(trainingExerciseMapper.map(exercises));
  }

  @PostMapping("/units/{unitId}/exercises")
  ResponseEntity<TrainingExerciseDto> postTrainingExercise(
      @PathVariable String planId,
      @PathVariable String unitId,
      @RequestParam String catalogExerciseId) throws ResourceNotFoundException {
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
      @RequestParam(required = false) String catalogExerciseId) throws ResourceNotFoundException {
    log.debug("Dto: {}", dto);
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var catalogExercise = catalogExerciseId == null ? null
        : catalogExerciseService.findCatalogExerciseByLocalId(catalogExerciseId);
    var updatedExercise = trainingExerciseService.updateTrainingExercise(exercise, catalogExercise, dto);
    var updatedExerciseDto = trainingExerciseMapper.map(updatedExercise);

    return ResponseEntity
        .ok(updatedExerciseDto);
  }

  @DeleteMapping("/exercises/{exerciseId}")
  ResponseEntity<DeletedObjectDto> deleteTrainingExercise(
      @PathVariable String planId,
      @PathVariable String exerciseId) {
    var deletedExerciseId = trainingExerciseService.deleteTrainingExerciseByLocalId(exerciseId);

    return ResponseEntity
        .ok(new DeletedObjectDto(deletedExerciseId));
  }
}
