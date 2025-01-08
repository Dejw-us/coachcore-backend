package pro.shapeit.api.training.exercise.catalog;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog-exercises")
@RequiredArgsConstructor
public class CatalogExerciseController {
  private final CatalogExerciseService catalogExerciseService;
  private final CatalogExerciseMapper catalogExerciseMapper;

  @GetMapping
  @Operation(
      summary = "Get all catalog exercises"
  )
  public ResponseEntity<?> getCatalogExercises() {
    var exercises = catalogExerciseService.finalAllCatalogExercises();
    var exercisesDto = exercises.stream()
        .map(catalogExerciseMapper::map)
        .toList();

    return ResponseEntity
        .ok(exercisesDto);
  }
}
