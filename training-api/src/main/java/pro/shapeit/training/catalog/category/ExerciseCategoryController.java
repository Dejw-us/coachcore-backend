package pro.shapeit.training.catalog.category;

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
import pro.shapeit.common.exception.ResourceNotFoundException;
import pro.shapeit.training.catalog.exercise.CatalogExerciseDto;
import pro.shapeit.training.catalog.exercise.CatalogExerciseMapper;
import pro.shapeit.training.catalog.exercise.CatalogExerciseService;
import pro.shapeit.training.catalog.exercise.CreateCatalogExerciseDto;

@RestController
@RequestMapping("/exercise-categories")
@RequiredArgsConstructor
public class ExerciseCategoryController {
  private final ExerciseCategoryService exerciseCategoryService;
  private final CatalogExerciseService catalogExerciseService;

  private final ExerciseCategoryMapper exerciseCategoryMapper;
  private final CatalogExerciseMapper catalogExerciseMapper;

  // --- GET ---

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
}
