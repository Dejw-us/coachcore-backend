package pro.shapeit.api.training.exercise;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.set.TrainingSetDto;
import pro.shapeit.api.training.set.TrainingSetMapper;
import pro.shapeit.api.training.set.TrainingSetService;

@RestController
@RequestMapping("/training-exercises")
@RequiredArgsConstructor
public class TrainingExerciseController {
  private final TrainingExerciseService trainingExerciseService;
  private final TrainingSetService trainingSetService;

  private final TrainingSetMapper trainingSetMapper;

  @PostMapping("/{exerciseLocalId}/sets")
  @Operation(
      summary = "Save new training set on exercise",
      responses = @ApiResponse(
          description = "Saved training set",
          responseCode = "201",
          content = @Content(
              schema = @Schema(implementation = TrainingSetDto.class)
          )
      )
  )
  public ResponseEntity<?> postTrainingSet(@PathVariable String exerciseLocalId) throws ResourceNotFoundException {
    var exerciseParent = trainingExerciseService.findTrainingExercise(exerciseLocalId);
    var savedSet = trainingSetService.saveTrainingSet(exerciseParent);
    var savedSetDto = trainingSetMapper.map(savedSet);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedSetDto);
  }
}