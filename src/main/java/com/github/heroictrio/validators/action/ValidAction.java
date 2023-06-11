package com.github.heroictrio.validators.action;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {ActionValidator.class})
public @interface ValidAction {

  String message() default "Invalid action choosen";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
