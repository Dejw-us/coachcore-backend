package pro.coachcore.training;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.training.catalog.category.ExerciseCategoryService;
import pro.coachcore.training.catalog.exercise.CatalogExerciseService;
import pro.coachcore.training.catalog.exercise.CreateCatalogExerciseDto;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition
@EnableJpaAuditing
public class TrainingServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(TrainingServiceApplication.class, args);
  }
}
