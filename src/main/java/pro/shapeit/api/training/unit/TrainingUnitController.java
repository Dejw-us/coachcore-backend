package pro.shapeit.api.training.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.training.exercise.CreateTrainingExerciseDto;
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


  @GetMapping("/{unitLocalId}")
  public ResponseEntity<?> getTrainingUnit(@PathVariable String unitLocalId) {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }

  @PostMapping("/{unitLocalId}/exercises")
  public ResponseEntity<?> getTrainingExercises(
      @PathVariable String unitLocalId,
      @RequestBody CreateTrainingExerciseDto dto
  ) {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var catalogExercise = catalogExerciseService.findCatalogExercise(dto.catalogExerciseLocalId());
    var trainingExercise = trainingExerciseService.saveTrainingExercise(unit, catalogExercise);

    return ResponseEntity
        .ok(trainingUnitMapper.map(unit));
  }

  @PatchMapping("/{unitLocalId}")
  public ResponseEntity<?> patchTrainingUnit(@PathVariable String unitLocalId, @RequestBody UpdateTrainingUnitDto dto) {
    var unit = trainingUnitService.updateTrainingUnit(unitLocalId, dto);
    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }
}
