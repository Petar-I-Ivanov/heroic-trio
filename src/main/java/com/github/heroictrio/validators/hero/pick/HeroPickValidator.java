package com.github.heroictrio.validators.hero.pick;

import static com.github.heroictrio.utilities.Constants.DWARF_PICK;
import static com.github.heroictrio.utilities.Constants.GNOME_PICK;
import static com.github.heroictrio.utilities.Constants.WIZARD_PICK;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class HeroPickValidator implements ConstraintValidator<ValidHeroPick, String> {

  @Override
  public boolean isValid(String heroPickValue, ConstraintValidatorContext context) {

    if (heroPickValue == null || heroPickValue.length() != 1) {
      return false;
    }

    char heroPick = heroPickValue.charAt(0);

    return List.of(DWARF_PICK, GNOME_PICK, WIZARD_PICK).contains(heroPick);
  }
}
