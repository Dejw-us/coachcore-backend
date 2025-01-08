package pro.shapeit.api.training.exercise.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseMapper;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseService;
import pro.shapeit.api.training.exercise.catalog.CreateCatalogExerciseDto;

@RestController
@RequestMapping("/catalogExercise-categories")
@RequiredArgsConstructor
public class ExerciseCategoryController {
  private final ExerciseCategoryService exerciseCategoryService;
  private final CatalogExerciseService catalogExerciseService;

  private final ExerciseCategoryMapper exerciseCategoryMapper;
  private final CatalogExerciseMapper catalogExerciseMapper;

  @GetMapping
  public ResponseEntity<?> getExerciseCategories() {
    var categories = exerciseCategoryService.findAllExerciseCategories();
    var categoriesDto = categories.stream()
        .map(exerciseCategoryMapper::map)
        .toList();

    return ResponseEntity
        .ok(categoriesDto);
  }

  @PostMapping("{categoryLocalId}/catalog-catalogExercise")
  public ResponseEntity<?> postCatalogExercise(
      @PathVariable String categoryLocalId,
      @RequestBody CreateCatalogExerciseDto dto
  ) {
    var category = exerciseCategoryService.findExerciseCategory(categoryLocalId);
    var savedExercise = catalogExerciseService.saveCatalogExercise(dto, category);
    var savedExerciseDto = catalogExerciseMapper.map(savedExercise);

    return ResponseEntity
        .ok(savedExerciseDto);
  }
}
