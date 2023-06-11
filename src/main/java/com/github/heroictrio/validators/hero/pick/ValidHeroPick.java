package com.github.heroictrio.validators.hero.pick;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {HeroPickValidator.class})
public @interface ValidHeroPick {

  String message() default "Invalid hero pick";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
