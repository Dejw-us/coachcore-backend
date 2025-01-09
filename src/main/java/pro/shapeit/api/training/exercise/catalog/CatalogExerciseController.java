package pro.shapeit.api.training.exercise.catalog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-exercises")
@RequiredArgsConstructor
public class CatalogExerciseController {
  private final CatalogExerciseService catalogExerciseService;
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
  public ResponseEntity<?> getCatalogExercises() {
    var exercises = catalogExerciseService.finalAllCatalogExercises();
    var exercisesDto = exercises.stream()
        .map(catalogExerciseMapper::map)
        .toList();

    return ResponseEntity
        .ok(exercisesDto);
  }
}
