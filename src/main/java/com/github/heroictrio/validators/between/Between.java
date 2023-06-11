package com.github.heroictrio.validators.between;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {BetweenValidator.class})
public @interface Between {

  String message() default "Value is not within the specified range";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  byte min() default Byte.MIN_VALUE;

  byte max() default Byte.MAX_VALUE;
}
