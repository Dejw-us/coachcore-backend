package pro.coachcore.training.plan.set;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.DeletedObjectDto;
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
  ResponseEntity<List<TrainingSetDto>> getSets(@PathVariable String exerciseId) {
    var sets = trainingSetService.getExerciseSets(exerciseId);

    return ResponseEntity.ok(trainingSetMapper.map(sets));
  }

  @PostMapping("/exercises/{exerciseId}/sets")
  ResponseEntity<TrainingSetDto> postTrainingSet(@PathVariable String planId,
      @PathVariable String exerciseId) throws ResourceNotFoundException {
    var exercise = trainingExerciseService.findTrainingExerciseByLocalId(exerciseId);
    var savedSet = trainingSetService.saveSet(exercise);
    var savedSetDto = trainingSetMapper.map(savedSet);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedSetDto);
  }

  @PatchMapping("/sets/{setId}")
  ResponseEntity<TrainingSetDto> patchTrainingSet(@PathVariable String planId,
      @PathVariable String setId, @RequestBody UpdateTrainingSetDto dto)
      throws ResourceNotFoundException {
    var set = trainingSetService.getSet(setId);
    var updatedSet = trainingSetService.updateSet(set, dto);
    var updatedSetDto = trainingSetMapper.map(updatedSet);

    return ResponseEntity.ok(updatedSetDto);
  }

  @DeleteMapping("/sets/{setId}")
  ResponseEntity<DeletedObjectDto> deleteTrainingSet(@PathVariable String planId,
      @PathVariable String setId) {
    var deletedSetId = trainingSetService.deleteSet(setId);

    return ResponseEntity.ok(new DeletedObjectDto(deletedSetId));
  }
}
