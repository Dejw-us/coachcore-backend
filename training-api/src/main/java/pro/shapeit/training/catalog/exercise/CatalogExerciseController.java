package pro.shapeit.training.catalog.exercise;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pro.coachcore.exception.ResourceNotFoundException;
import pro.shapeit.training.catalog.category.ExerciseCategoryService;

import java.util.List;

@RestController
@RequestMapping("/v1/catalog-exercises")
@RequiredArgsConstructor
public class CatalogExerciseController {
  private final CatalogExerciseService catalogExerciseService;
  private final ExerciseCategoryService exerciseCategoryService;

  private final CatalogExerciseMapper catalogExerciseMapper;

  @GetMapping
  @Operation(
      summary = "Get all catalog exercises",
      responses = @ApiResponse(
          description = "List of all catalog exercises",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = CatalogExerciseDto.class)
              )
          )
      ),
      tags = "Catalog"
  )
  public ResponseEntity<List<CatalogExerciseDto>> getCatalogExercises() {
    var exercises = catalogExerciseService.finalAllCatalogExercises();
    var exercisesDto = exercises.stream()
        .map(catalogExerciseMapper::map)
        .toList();

    return ResponseEntity
        .ok(exercisesDto);
  }

  @PostMapping
  @Operation(
      summary = "Save new catalog exercise corresponding to the given category",
      responses = @ApiResponse(
          description = "Created catalog exercise",
          responseCode = "201",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CatalogExerciseDto.class)
          )
      ),
      tags = "Catalog"
  )
  public ResponseEntity<?> postCatalogExercise(
      @RequestParam String categoryId,
      @RequestBody CreateCatalogExerciseDto dto
  ) throws ResourceNotFoundException {
    var category = exerciseCategoryService.findExerciseCategory(categoryId);
    var savedExercise = catalogExerciseService.saveCatalogExercise(dto, category);
    var savedExerciseDto = catalogExerciseMapper.map(savedExercise);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedExerciseDto);
  }
}
