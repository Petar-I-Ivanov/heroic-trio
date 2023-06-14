package com.github.heroictrio.validators;

import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidScenarioValidator implements ConstraintValidator<ValidScenario, Input> {

  private static final String MOVEMENT = "movement";
  private static final String ABILITY = "ability";

  @Override
  public boolean isValid(Input input, ConstraintValidatorContext context) {

    try {
      return isAnyScenarioAvailable(input);
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean isAnyScenarioAvailable(Input input) {

    return isMovementScenario(input) || isGnomeAbilityScenario(input)
        || isDwarfAbilityScenario(input) || isWizardAbilityScenario(input);
  }

  private static boolean isMovementScenario(Input input) {
    return input.getAction().equals(MOVEMENT) && input.getDirection() != null;
  }

  private static boolean isGnomeAbilityScenario(Input input) {

    String stringGnomePick = String.valueOf(Constants.GNOME_PICK);

    return input.getHeroPick().equals(stringGnomePick) && input.getAction().equals(ABILITY)
        && input.getDirection() != null && input.getNumberOfSquares() != -1;
  }

  private static boolean isDwarfAbilityScenario(Input input) {

    String stringDwarfPick = String.valueOf(Constants.DWARF_PICK);
    Position defaultPosition = new Position(-1, -1);

    return input.getHeroPick().equals(stringDwarfPick) && input.getAction().equals(ABILITY)
        && !input.getPositionOne().equals(defaultPosition)
        && !input.getPositionTwo().equals(defaultPosition);
  }

  private static boolean isWizardAbilityScenario(Input input) {

    String stringWizardPick = String.valueOf(Constants.WIZARD_PICK);
    Position defaultPosition = new Position(-1, -1);

    return input.getHeroPick().equals(stringWizardPick) && input.getAction().equals(ABILITY)
        && !input.getPositionOne().equals(defaultPosition)
        && !input.getPositionTwo().equals(defaultPosition) && input.getSortType() != null;
  }
}
