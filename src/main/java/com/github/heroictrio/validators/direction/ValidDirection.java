package com.github.heroictrio.validators.direction;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {DirectionValidator.class})
public @interface ValidDirection {

  String message() default "Invalid direction";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
