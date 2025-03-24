package pro.coachcore.oauth2.server;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pro.coachcore.exception.ResourceAlreadyExistsException;
import pro.coachcore.oauth2.server.security.RsaKeyProperties;
import pro.coachcore.oauth2.server.user.admin.AdminCredentialsProperties;
import pro.coachcore.oauth2.server.user.role.UserRoleService;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({RsaKeyProperties.class, AdminCredentialsProperties.class})
public class AuthServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

  @Bean
  @Order(1)
  ApplicationRunner defaultRolesRunner(UserRoleService userService) {
    return args -> {
      try {
        userService.saveDefaultRole("USER");
        userService.saveDefaultRole("ADMIN");
        userService.saveDefaultRole("TRAINER");
      } catch (ResourceAlreadyExistsException exception) {
      }
    };
  }
}
