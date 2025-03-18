package pro.coachcore.training.catalog;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.training.catalog.category.ExerciseCategoryService;
import pro.coachcore.training.catalog.exercise.CatalogExerciseService;
import pro.coachcore.training.catalog.exercise.CreateCatalogExerciseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultCatalogRunner implements ApplicationRunner {
  private final ExerciseCategoryService exerciseCategoryService;
  private final CatalogExerciseService catalogExerciseService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      var calisthenics =
          exerciseCategoryService.saveExerciseCategory("Calisthenics", "Bodyweight training");
      var weights = exerciseCategoryService.saveExerciseCategory("Weights", "For gym lovers");
      var crossfit = exerciseCategoryService.saveExerciseCategory("Crossfit", "Are you gay?");

      catalogExerciseService.saveCatalogExercise(new CreateCatalogExerciseDto("pull-up"),
          calisthenics);
      catalogExerciseService.saveCatalogExercise(new CreateCatalogExerciseDto("squat"), weights);
      catalogExerciseService
          .saveCatalogExercise(new CreateCatalogExerciseDto("low quality pull-up"), crossfit);
    } catch (ResourceAlreadyExistsException ignore) {
    }
  }
}
