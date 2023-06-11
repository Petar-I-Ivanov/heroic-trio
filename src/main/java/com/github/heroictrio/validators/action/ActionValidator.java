package com.github.heroictrio.validators.action;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ActionValidator implements ConstraintValidator<ValidAction, String> {

  private static final String MOVEMENT = "movement";
  private static final String ABILITY = "ability";

  @Override
  public boolean isValid(String action, ConstraintValidatorContext context) {

    return action.equals(MOVEMENT) || action.equals(ABILITY);
  }
}
