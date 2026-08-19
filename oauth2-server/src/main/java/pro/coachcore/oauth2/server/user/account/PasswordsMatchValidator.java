package pro.coachcore.oauth2.server.user.account;

import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PasswordsMatchValidator
    implements ConstraintValidator<PasswordsMatch, RegisterUserDto> {
  @Override
  public boolean isValid(RegisterUserDto dto, ConstraintValidatorContext context) {
    var isValid = dto.getConfirmPassword().equals(dto.getPassword());
    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Passwords do not match")
          .addPropertyNode("confirmPassword").addConstraintViolation();
    }
    return isValid;
  }
}
