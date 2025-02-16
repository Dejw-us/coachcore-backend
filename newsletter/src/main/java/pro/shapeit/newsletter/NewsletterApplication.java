package pro.shapeit.newsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pro.shapeit.exception.DefaultGlobalExceptionHandler;

@Import(DefaultGlobalExceptionHandler.class)
@SpringBootApplication
public class NewsletterApplication {
  public static void main(String[] args) {
    SpringApplication.run(NewsletterApplication.class, args);
  }
}