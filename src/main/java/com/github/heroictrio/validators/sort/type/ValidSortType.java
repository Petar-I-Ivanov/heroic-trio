package com.github.heroictrio.validators.sort.type;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {SortTypeValidator.class})
public @interface ValidSortType {

  String message() default "Invalid sort type choosen";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
