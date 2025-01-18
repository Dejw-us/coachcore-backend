package pro.shapeit.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pro.shapeit.api.validation.annotation.ValidDayOfWeek;

import java.time.DayOfWeek;

import static org.apache.commons.lang3.EnumUtils.getEnum;

@Component
public class DayOfWeekValidator implements ConstraintValidator<ValidDayOfWeek, String> {
  @Override
  public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
    return getEnum(DayOfWeek.class, string) != null;
  }
}
