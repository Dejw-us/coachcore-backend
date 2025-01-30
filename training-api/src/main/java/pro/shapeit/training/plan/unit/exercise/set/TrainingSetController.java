package pro.shapeit.training.plan.unit.exercise.set;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.dto.MessageDto;
import pro.shapeit.exception.ResourceNotFoundException;
import pro.shapeit.training.plan.unit.exercise.TrainingExerciseService;

import static pro.shapeit.util.ControllerUtils.deleteResponse;

@RestController
@RequestMapping("/v1/training-plans/{planId}")
@RequiredArgsConstructor
public class TrainingSetController {
  private final TrainingSetService trainingSetService;
  private final TrainingExerciseService trainingExerciseService;

  private final TrainingSetMapper trainingSetMapper;

  // --- POST ---

  @PostMapping("/exercises/{exerciseId}/sets")
  ResponseEntity<TrainingSetDto> postTrainingSet(
      @PathVariable String planId,
      @PathVariable String exerciseId
  ) throws ResourceNotFoundException {
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var savedSet = trainingSetService.saveTrainingSet(exercise);
    var savedSetDto = trainingSetMapper.map(savedSet);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedSetDto);
  }

  // --- PATCH ---

  @PatchMapping("/sets/{setId}")
  ResponseEntity<TrainingSetDto> patchTrainingSet(
      @PathVariable String planId,
      @PathVariable String setId,
      @RequestBody UpdateTrainingSetDto dto
  ) throws ResourceNotFoundException {
    var set = trainingSetService.findTrainingSetByLocalId(setId);
    var updatedSet = trainingSetService.updateTrainingSet(set, dto);
    var updatedSetDto = trainingSetMapper.map(updatedSet);

    return ResponseEntity
        .ok(updatedSetDto);
  }

  // --- DELETE ---

  @DeleteMapping("/sets/{setId}")
  ResponseEntity<MessageDto> deleteTrainingSet(
      @PathVariable String planId,
      @PathVariable String setId
  ) {
    var isDeleted = trainingSetService.deleteTrainingSetByLocalId(setId);

    return deleteResponse(isDeleted, "training set");
  }
}
