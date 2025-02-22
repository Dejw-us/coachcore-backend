package pro.coachcore.auth.user.admin;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.coachcore.auth.user.UserService;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
class DefaultAdminAccountApplicationRunner implements ApplicationRunner {
  private final AdminCredentialsProperties adminCredentials;
  private final UserService userService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var admin = userService.registerAdmin(
        adminCredentials.username(),
        adminCredentials.password(),
        adminCredentials.email());

    if (admin == null) {
      log.info("Failed to create default admin");
    }
  }
}
