package pro.coachcore.training.catalog.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/exercise-categories")
@RequiredArgsConstructor
public class ExerciseCategoryController {
  private final ExerciseCategoryService exerciseCategoryService;
  private final ExerciseCategoryMapper exerciseCategoryMapper;

  @GetMapping
  public ResponseEntity<List<ExerciseCategoryDto>> getExerciseCategories() {
    var categories = exerciseCategoryService.findAllExerciseCategories();
    var categoriesDto = categories.stream().map(exerciseCategoryMapper::map).toList();

    return ResponseEntity.ok(categoriesDto);
  }
}
