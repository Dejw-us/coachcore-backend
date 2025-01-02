package pro.shapeit.api.annotation.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pro.shapeit.api.validation.LocalIdValidator;

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
