package pro.coachcore.training.plan.set;

import lombok.RequiredArgsConstructor;

import static pro.coachcore.util.ControllerUtils.deleteResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pro.coachcore.dto.DeletedObjectDto;
import pro.coachcore.dto.MessageDto;
import pro.coachcore.exception.ResourceNotFoundException;
import pro.coachcore.training.plan.exercise.TrainingExerciseService;

@RestController
@RequestMapping("/v1/training-plans/{planId}")
@RequiredArgsConstructor
public class TrainingSetController {
  private final TrainingSetService trainingSetService;
  private final TrainingExerciseService trainingExerciseService;

  private final TrainingSetMapper trainingSetMapper;

  @GetMapping("/exercises/{exerciseId}/sets")
  ResponseEntity<List<TrainingSetDto>> getSets(
      @PathVariable String exerciseId) {
    var sets = trainingSetService.findAllByTrainingExerciseLocalId(exerciseId);

    return ResponseEntity
        .ok(trainingSetMapper.map(sets));
  }

  @PostMapping("/exercises/{exerciseId}/sets")
  ResponseEntity<TrainingSetDto> postTrainingSet(
      @PathVariable String planId,
      @PathVariable String exerciseId) throws ResourceNotFoundException {
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var savedSet = trainingSetService.saveTrainingSet(exercise);
    var savedSetDto = trainingSetMapper.map(savedSet);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedSetDto);
  }

  @PatchMapping("/sets/{setId}")
  ResponseEntity<TrainingSetDto> patchTrainingSet(
      @PathVariable String planId,
      @PathVariable String setId,
      @RequestBody UpdateTrainingSetDto dto) throws ResourceNotFoundException {
    var set = trainingSetService.findTrainingSetByLocalId(setId);
    var updatedSet = trainingSetService.updateTrainingSet(set, dto);
    var updatedSetDto = trainingSetMapper.map(updatedSet);

    return ResponseEntity
        .ok(updatedSetDto);
  }

  @DeleteMapping("/sets/{setId}")
  ResponseEntity<DeletedObjectDto> deleteTrainingSet(
      @PathVariable String planId,
      @PathVariable String setId) {
    var deletedSetId = trainingSetService.deleteTrainingSetByLocalId(setId);

    return ResponseEntity
        .ok(new DeletedObjectDto(deletedSetId));
  }
}
