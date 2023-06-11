package com.github.heroictrio.validator;

import com.github.heroictrio.utilities.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class InputValidator implements ConstraintValidator<ValidInput, Input> {

  @Override
  public boolean isValid(Input value, ConstraintValidatorContext context) {

    String heroPickStr = value.getHeroPick();
    String directionStr = value.getDirection();

    if (heroPickStr.length() != 1 && directionStr.length() != 1) {
      return false;
    }

    char heroPick = heroPickStr.charAt(0);
    char direction = directionStr.charAt(0);

    return isCharValidHeroPick(heroPick) && isCharValidDirection(direction);
  }

  private static boolean isCharValidHeroPick(char heroPick) {

    return Arrays.asList(Constants.DWARF_PICK, Constants.GNOME_PICK, Constants.WIZARD_PICK)
        .contains(heroPick);
  }

  private static boolean isCharValidDirection(char direction) {

    return Arrays.asList(Constants.FORWARD_MOVE, Constants.RIGHT_MOVE, Constants.BACKWARD_MOVE,
        Constants.LEFT_MOVE).contains(direction);
  }
}
