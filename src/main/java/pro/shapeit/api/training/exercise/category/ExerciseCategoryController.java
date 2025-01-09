package pro.shapeit.api.training.exercise.category;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.common.exception.ResourceNotFoundException;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseMapper;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseService;
import pro.shapeit.api.training.exercise.catalog.CreateCatalogExerciseDto;

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
      summary = "Get all exercise categories"
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
      summary = "Save new catalog exercise corresponding to the given category"
  )
  public ResponseEntity<?> postCatalogExercise(
      @PathVariable String categoryLocalId,
      @RequestBody CreateCatalogExerciseDto dto
  ) throws ResourceNotFoundException {
    var category = exerciseCategoryService.findExerciseCategory(categoryLocalId);
    var savedExercise = catalogExerciseService.saveCatalogExercise(dto, category);
    var savedExerciseDto = catalogExerciseMapper.map(savedExercise);

    return ResponseEntity
        .ok(savedExerciseDto);
  }
}
