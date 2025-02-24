package pro.coachcore.auth;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import pro.coachcore.auth.security.RsaKeyProperties;
import pro.coachcore.auth.user.UserService;
import pro.coachcore.auth.user.admin.AdminCredentialsProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({ RsaKeyProperties.class, AdminCredentialsProperties.class })
public class AuthServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

  @Bean
  @Order(1)
  ApplicationRunner defaultRolesRunner(UserService userService) {
    return args -> {
      userService.saveDefaultRole("USER");
      userService.saveDefaultRole("ADMIN");
      userService.saveDefaultRole("TRAINER");
    };
  }
}