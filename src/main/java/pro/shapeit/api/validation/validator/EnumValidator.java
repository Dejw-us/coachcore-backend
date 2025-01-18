package pro.shapeit.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pro.shapeit.api.validation.annotation.ValidEnum;

import java.util.Arrays;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private List<String> acceptedValues;

  @Override
  public void initialize(ValidEnum annotation) {
    acceptedValues = Arrays.stream(annotation.value().getEnumConstants())
        .map(Enum::name)
        .toList();
  }

  @Override
  public boolean isValid(String string, ConstraintValidatorContext context) {
    return acceptedValues.contains(string);
  }
}
