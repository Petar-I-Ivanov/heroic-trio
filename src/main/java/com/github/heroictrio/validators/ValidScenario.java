package com.github.heroictrio.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidScenarioValidator.class)
public @interface ValidScenario {

  String message() default "Inavailable scenario input.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
