package pro.shapeit.api.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pro.shapeit.api.validation.validator.DayOfWeekValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DayOfWeekValidator.class)
public @interface ValidDayOfWeek {
  String message() default """
      Invalid day of week.
      Valid days of week: [MONDAY, WEDNESDAY, SATURDAY, THURSDAY, TUESDAY, FRIDAY, SUNDAY]
      """;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
