package pro.shapeit.api.training.unit;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.dto.MessageDto;
import pro.shapeit.api.training.exercise.CreateTrainingExerciseDto;
import pro.shapeit.api.training.exercise.TrainingExerciseMapper;
import pro.shapeit.api.training.exercise.TrainingExerciseService;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseService;

@RestController
@RequestMapping("/training-units")
@RequiredArgsConstructor
public class TrainingUnitController {
  private final TrainingUnitService trainingUnitService;
  private final CatalogExerciseService catalogExerciseService;
  private final TrainingExerciseService trainingExerciseService;

  private final TrainingUnitMapper trainingUnitMapper;
  private final TrainingExerciseMapper trainingExerciseMapper;


  @GetMapping("/{unitLocalId}")
  @Operation(
      summary = "Get specific training unit"
  )
  public ResponseEntity<?> getTrainingUnit(@PathVariable String unitLocalId) {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }

  @PostMapping("/{unitLocalId}/exercises")
  @Operation(
      summary = "Get all exercises of training unit"
  )
  public ResponseEntity<?> getTrainingExercises(
      @PathVariable String unitLocalId,
      @RequestBody CreateTrainingExerciseDto dto
  ) {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var catalogExercise = catalogExerciseService.findCatalogExercise(dto.catalogExerciseLocalId());
    var savedExercise = trainingExerciseService.saveTrainingExercise(unit, catalogExercise);

    if (savedExercise == null) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(new MessageDto("Failed to save training exercise"));
    }

    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }

  @PatchMapping("/{unitLocalId}")
  @Operation(
      summary = "Update training unit"
  )
  public ResponseEntity<?> patchTrainingUnit(@PathVariable String unitLocalId, @RequestBody UpdateTrainingUnitDto dto) {
    var unit = trainingUnitService.updateTrainingUnit(unitLocalId, dto);
    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }
}
