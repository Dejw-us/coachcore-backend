package pro.coachcore.newsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import pro.coachcore.exception.handler.DefaultGlobalExceptionHandler;

@EnableMethodSecurity
@EnableConfigurationProperties
@Import(DefaultGlobalExceptionHandler.class)
@SpringBootApplication
public class NewsletterApplication {
  public static void main(String[] args) {
    SpringApplication.run(NewsletterApplication.class, args);
  }
}