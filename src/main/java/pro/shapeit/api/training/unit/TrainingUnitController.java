package pro.shapeit.api.training.unit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.exercise.CreateTrainingExerciseDto;
import pro.shapeit.api.training.exercise.TrainingExerciseDto;
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
      summary = "Get specific training unit",
      responses = @ApiResponse(
          description = "Training unit with specific local id",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingUnitDto.class)
          )
      )
  )
  public ResponseEntity<?> getTrainingUnit(@PathVariable String unitLocalId) throws ResourceNotFoundException {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var unitDto = trainingUnitMapper.map(unit);

    return ResponseEntity
        .ok(unitDto);
  }

  @PostMapping("/{unitLocalId}/exercises")
  @Operation(
      summary = "Save new exercise on training unit",
      responses = @ApiResponse(
          description = "Saved training exercise",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingExerciseDto.class)
          )
      )
  )
  public ResponseEntity<?> getTrainingExercises(
      @PathVariable String unitLocalId,
      @RequestBody CreateTrainingExerciseDto dto
  ) throws ResourceNotFoundException {
    var unit = trainingUnitService.findTrainingUnit(unitLocalId);
    var catalogExercise = catalogExerciseService.findCatalogExercise(dto.catalogExerciseLocalId());
    var savedExercise = trainingExerciseService.saveTrainingExercise(unit, catalogExercise);
    var savedExerciseDto = trainingExerciseMapper.map(savedExercise);

    return ResponseEntity
        .ok(savedExerciseDto);
  }

  @PatchMapping("/{unitLocalId}")
  @Operation(
      summary = "Update training unit",
      responses = @ApiResponse(
          description = "Updated training unit",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TrainingUnitDto.class)
          )
      )
  )
  public ResponseEntity<?> patchTrainingUnit(@PathVariable String unitLocalId, @RequestBody UpdateTrainingUnitDto dto) throws ResourceNotFoundException {
    var updatedUnit = trainingUnitService.updateTrainingUnit(unitLocalId, dto);
    var updatedUnitDto = trainingUnitMapper.map(updatedUnit);

    return ResponseEntity
        .ok(updatedUnitDto);
  }
}
