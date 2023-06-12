package com.github.heroictrio.validators;

import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import com.github.heroictrio.validators.action.ValidAction;
import com.github.heroictrio.validators.between.Between;
import com.github.heroictrio.validators.direction.ValidDirection;
import com.github.heroictrio.validators.hero.pick.ValidHeroPick;
import com.github.heroictrio.validators.sort.type.ValidSortType;
import lombok.Data;

@Data
@ValidScenario
public class Input {

  @ValidHeroPick
  private String heroPick;

  @ValidAction
  private String action;

  @ValidDirection
  private String direction;

  @Between(min = 0, max = Constants.MAX_ROW - 1)
  private byte rowOne;

  @Between(min = 0, max = Constants.MAX_COL - 1)
  private byte colOne;

  @Between(min = 0, max = Constants.MAX_ROW - 1)
  private byte rowTwo;

  @Between(min = 0, max = Constants.MAX_COL - 1)
  private byte colTwo;

  @Between(min = 2, max = 3)
  private byte numberOfSquares;

  @ValidSortType
  private String sortType;

  public Position getPositionOne() {
    return new Position(this.rowOne, this.colOne);
  }

  public Position getPositionTwo() {
    return new Position(this.rowTwo, this.colTwo);
  }
}
