package com.github.heroictrio.validators.between;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BetweenValidator implements ConstraintValidator<Between, Byte> {

  private byte min;
  private byte max;

  @Override
  public void initialize(Between constraintAnnotation) {

    this.min = constraintAnnotation.min();
    this.max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(Byte value, ConstraintValidatorContext context) {
    return this.min <= value && this.max >= value;
  }
}
