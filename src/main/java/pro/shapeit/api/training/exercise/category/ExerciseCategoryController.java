package pro.shapeit.api.training.exercise.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.api.training.exercise.catalog.CatalogExerciseService;
import pro.shapeit.api.training.exercise.catalog.CreateCatalogExerciseDto;

@RestController
@RequestMapping("/catalogExercise-categories")
@RequiredArgsConstructor
public class ExerciseCategoryController {
  private final ExerciseCategoryService exerciseCategoryService;
  private final CatalogExerciseService catalogExerciseService;

  @GetMapping
  public ResponseEntity<?> getExerciseCategories() {
    return ResponseEntity
        .ok(exerciseCategoryService.findAllExerciseCategories());
  }

  @PostMapping("{categoryLocalId}/catalog-catalogExercise")
  public ResponseEntity<?> postCatalogExercise(
      @PathVariable String categoryLocalId,
      @RequestBody CreateCatalogExerciseDto dto
  ) {
    var category = exerciseCategoryService.findExerciseCategory(categoryLocalId);
    return ResponseEntity
        .ok(catalogExerciseService.saveCatalogExercise(dto, category));
  }
}
