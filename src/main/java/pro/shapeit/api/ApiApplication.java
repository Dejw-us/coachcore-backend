package pro.shapeit.api;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.shapeit.api.training.exercise.category.ExerciseCategoryService;

@SpringBootApplication
public class ApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

  @Bean
  public ApplicationRunner defaultExerciseCategoryCreator(ExerciseCategoryService exerciseCategoryService) {
    return args -> {
      exerciseCategoryService.saveExerciseCategory("Calisthenics");
      exerciseCategoryService.saveExerciseCategory("Weights");
      exerciseCategoryService.saveExerciseCategory("Home");
    };
  }
}
