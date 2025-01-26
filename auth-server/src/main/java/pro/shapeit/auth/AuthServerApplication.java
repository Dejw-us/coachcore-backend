package pro.shapeit.auth;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pro.shapeit.auth.user.UserService;

@SpringBootApplication
@EnableJpaAuditing
public class AuthServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

  @Bean
  public ApplicationRunner defaultRolesRunner(UserService userService) {
    return args -> {
      userService.saveDefaultRole("USER");
      userService.saveDefaultRole("ADMIN");
      userService.saveDefaultRole("TRAINER");
    };
  }
}