package com.github.heroictrio.validators.direction;

import static com.github.heroictrio.utilities.Constants.BACKWARD_MOVE;
import static com.github.heroictrio.utilities.Constants.FORWARD_MOVE;
import static com.github.heroictrio.utilities.Constants.LEFT_MOVE;
import static com.github.heroictrio.utilities.Constants.RIGHT_MOVE;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class DirectionValidator implements ConstraintValidator<ValidDirection, String> {

  @Override
  public boolean isValid(String directionValue, ConstraintValidatorContext context) {

    if (directionValue.length() != 1) {
      return false;
    }

    char direction = directionValue.charAt(0);
    return List.of(FORWARD_MOVE, RIGHT_MOVE, BACKWARD_MOVE, LEFT_MOVE).contains(direction);
  }
}
