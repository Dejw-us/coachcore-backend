package pro.shapeit.backend.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pro.shapeit.backend.validation.annotation.ValidEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Set<String> acceptedValues;

  @Override
  public void initialize(ValidEnum annotation) {
    acceptedValues = Arrays.stream(annotation.value().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String string, ConstraintValidatorContext context) {
    return string != null && acceptedValues.contains(string);
  }
}
