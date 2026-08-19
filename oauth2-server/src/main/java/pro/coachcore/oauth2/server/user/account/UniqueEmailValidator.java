package pro.coachcore.oauth2.server.user.account;

import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import pro.coachcore.oauth2.server.user.UserService;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
  private final UserService userService;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return !userService.isEmailTaken(email);
  }
}
