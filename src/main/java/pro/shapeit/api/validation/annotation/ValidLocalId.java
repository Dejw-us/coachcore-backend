package pro.shapeit.api.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pro.shapeit.api.validation.validator.LocalIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LocalIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalId {
  String message() default "Invalid localId";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
