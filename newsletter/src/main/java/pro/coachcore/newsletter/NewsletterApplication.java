package pro.coachcore.newsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import pro.coachcore.exception.handler.DefaultGlobalExceptionHandler;

@Import(DefaultGlobalExceptionHandler.class)
@SpringBootApplication
public class NewsletterApplication {
  public static void main(String[] args) {
    SpringApplication.run(NewsletterApplication.class, args);
  }
}