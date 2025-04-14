package pro.coachcore.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
@EnableJpaAuditing
public class TrainingServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(TrainingServiceApplication.class, args);
  }
}
