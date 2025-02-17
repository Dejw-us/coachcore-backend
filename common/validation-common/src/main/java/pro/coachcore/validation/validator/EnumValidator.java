package pro.coachcore.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pro.coachcore.validation.annotation.ValidEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Set<String> acceptedValues;
  private boolean acceptNull;

  @Override
  public void initialize(ValidEnum annotation) {
    acceptedValues = Arrays.stream(annotation.value().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toSet());
    acceptNull = annotation.acceptNull();
  }

  @Override
  public boolean isValid(String string, ConstraintValidatorContext context) {
    if (string == null) {
      return acceptNull;
    }
    return acceptedValues.contains(string);
  }
}
