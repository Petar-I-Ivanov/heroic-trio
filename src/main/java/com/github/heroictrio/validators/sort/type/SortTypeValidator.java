package com.github.heroictrio.validators.sort.type;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SortTypeValidator implements ConstraintValidator<ValidSortType, String> {

  private static final String ASCENDING = "ascending";
  private static final String DESCENDING = "descending";

  @Override
  public boolean isValid(String sortType, ConstraintValidatorContext context) {

    return sortType.equals(ASCENDING) || sortType.equals(DESCENDING);
  }
}
