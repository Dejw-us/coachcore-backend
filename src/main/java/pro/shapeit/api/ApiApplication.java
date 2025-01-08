package pro.shapeit.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.shapeit.api.training.exercise.category.ExerciseCategoryService;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Shapeit PRO API docs")
)
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
