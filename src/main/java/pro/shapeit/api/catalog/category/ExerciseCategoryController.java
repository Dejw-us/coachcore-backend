package pro.shapeit.api.catalog.category;

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
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.catalog.exercise.CatalogExerciseDto;
import pro.shapeit.api.catalog.exercise.CatalogExerciseMapper;
import pro.shapeit.api.catalog.exercise.CatalogExerciseService;
import pro.shapeit.api.catalog.exercise.CreateCatalogExerciseDto;

@RestController
@RequestMapping("/exercise-categories")
@RequiredArgsConstructor
public class ExerciseCategoryController {
  private final ExerciseCategoryService exerciseCategoryService;
  private final CatalogExerciseService catalogExerciseService;

  private final ExerciseCategoryMapper exerciseCategoryMapper;
  private final CatalogExerciseMapper catalogExerciseMapper;

  @GetMapping
  @Operation(
      summary = "Get all exercise categories",
      responses = @ApiResponse(
          description = "List of all exercise categories",
          responseCode = "200",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = ExerciseCategoryDto.class)
              )
          )
      ),
      tags = "Catalog"
  )
  public ResponseEntity<?> getExerciseCategories() {
    var categories = exerciseCategoryService.findAllExerciseCategories();
    var categoriesDto = categories.stream()
        .map(exerciseCategoryMapper::map)
        .toList();

    return ResponseEntity
        .ok(categoriesDto);
  }

  @PostMapping("{categoryLocalId}/catalog-exercises")
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
      @PathVariable String categoryLocalId,
      @RequestBody CreateCatalogExerciseDto dto
  ) throws ResourceNotFoundException {
    var category = exerciseCategoryService.findExerciseCategory(categoryLocalId);
    var savedExercise = catalogExerciseService.saveCatalogExercise(dto, category);
    var savedExerciseDto = catalogExerciseMapper.map(savedExercise);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedExerciseDto);
  }
}
