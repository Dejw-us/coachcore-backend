package pro.coachcore.training;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import pro.coachcore.training.catalog.category.ExerciseCategoryService;
import pro.coachcore.training.catalog.exercise.CatalogExerciseService;
import pro.coachcore.training.catalog.exercise.CreateCatalogExerciseDto;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition
@EnableJpaAuditing
public class
TrainingApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(TrainingApiApplication.class, args);
  }

  @Bean
  ApplicationRunner setupCatalog(
      ExerciseCategoryService exerciseCategoryService,
      CatalogExerciseService catalogExerciseService
  ) {
    return args -> {
      var calisthenics = exerciseCategoryService.saveExerciseCategory("Calisthenics", "Bodyweight training");
      var weights = exerciseCategoryService.saveExerciseCategory("Weights", "For gym lovers");
      var crossfit = exerciseCategoryService.saveExerciseCategory("Crossfit", "Are you gay?");

      catalogExerciseService.saveCatalogExercise(new CreateCatalogExerciseDto("pull-up"), calisthenics);
      catalogExerciseService.saveCatalogExercise(new CreateCatalogExerciseDto("squat"), weights);
      catalogExerciseService.saveCatalogExercise(new CreateCatalogExerciseDto("low quality pull-up"), crossfit);
    };
  }
}
