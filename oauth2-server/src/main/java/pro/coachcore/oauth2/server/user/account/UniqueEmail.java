package pro.coachcore.oauth2.server.user.account;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Constraint(validatedBy =UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
 String message() default "Email is taken";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
