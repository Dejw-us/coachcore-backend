package pro.shapeit.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pro.shapeit.api.annotation.validation.ValidLocalId;

public class LocalIdValidator implements ConstraintValidator<ValidLocalId, String> {
  private static final String REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

  @Override
  public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
    if (string == null || string.length() != 36) {
      return false;
    }
    return string.matches(REGEX);
  }
}
