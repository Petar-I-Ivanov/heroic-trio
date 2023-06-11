package com.github.heroictrio.validators;

import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.validators.action.ValidAction;
import com.github.heroictrio.validators.between.Between;
import com.github.heroictrio.validators.direction.ValidDirection;
import com.github.heroictrio.validators.groups.DwarfAbilityGroup;
import com.github.heroictrio.validators.groups.GnomeAbilityGroup;
import com.github.heroictrio.validators.groups.MovementGroup;
import com.github.heroictrio.validators.groups.WizardAbilityGroup;
import com.github.heroictrio.validators.hero.pick.ValidHeroPick;
import com.github.heroictrio.validators.sort.type.ValidSortType;
import jakarta.validation.GroupSequence;
import lombok.Data;

@Data
@GroupSequence({MovementGroup.class, GnomeAbilityGroup.class, DwarfAbilityGroup.class,
    WizardAbilityGroup.class})
public class Input {

  @ValidHeroPick
  private String heroPick;

  @ValidAction
  private String action;

  @ValidDirection(groups = {MovementGroup.class, GnomeAbilityGroup.class})
  private String direction;

  @ValidSortType(groups = WizardAbilityGroup.class)
  private String sortType;

  @Between(min = 0, max = Constants.MAX_ROW - 1,
      groups = {WizardAbilityGroup.class, DwarfAbilityGroup.class})
  private byte rowOne;

  @Between(min = 0, max = Constants.MAX_COL - 1,
      groups = {WizardAbilityGroup.class, DwarfAbilityGroup.class})
  private byte colOne;

  @Between(min = 0, max = Constants.MAX_ROW - 1,
      groups = {WizardAbilityGroup.class, DwarfAbilityGroup.class})
  private byte rowTwo;

  @Between(min = 0, max = Constants.MAX_COL - 1,
      groups = {WizardAbilityGroup.class, DwarfAbilityGroup.class})
  private byte colTwo;

  @Between(min = 2, max = 3, groups = GnomeAbilityGroup.class)
  private byte numberOfSquares;
}
