package pro.shapeit.api.training.exercise;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.training.set.TrainingSetMapper;
import pro.shapeit.api.training.set.TrainingSetService;

@RestController
@RequestMapping("/training-exercises")
@RequiredArgsConstructor
public class TrainingExerciseController {
  private final TrainingExerciseService trainingExerciseService;
  private final TrainingSetService trainingSetService;
  private final TrainingExerciseMapper trainingExerciseMapper;
  private final TrainingSetMapper trainingSetMapper;

  @PostMapping("/{exerciseLocalId}/sets")
  @Operation(
      summary = "Save new training set on exercise"
  )
  public ResponseEntity<?> postTrainingSet(@PathVariable String exerciseLocalId) {
    var exerciseParent = trainingExerciseService.findTrainingExercise(exerciseLocalId);
    var savedSet = trainingSetService.saveTrainingSet(exerciseParent);
    var savedSetDto = trainingSetMapper.map(savedSet);

    return ResponseEntity
        .ok(savedSetDto);
  }
}