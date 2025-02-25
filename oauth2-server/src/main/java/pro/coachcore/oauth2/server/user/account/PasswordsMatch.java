package pro.coachcore.oauth2.server.user.account;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {
  String message() default "Passwords do not match";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
