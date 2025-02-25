package pro.coachcore.oauth2.server.user.account;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import pro.coachcore.oauth2.server.user.UserService;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
  private final UserService userService;

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return !userService.isUsernameTaken(username);
  }
}

