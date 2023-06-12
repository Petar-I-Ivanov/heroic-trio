package com.github.heroictrio.utilities;

import lombok.Data;

@Data
public class Position {

  private byte row;
  private byte col;

  public Position(int row, int col) {
    this.row = (byte) row;
    this.col = (byte) col;
  }

  public static boolean isPositionInBorders(Position position) {

    return (position.getRow() >= 0 && position.getRow() < Constants.MAX_ROW)
        && (position.getCol() >= 0 && position.getCol() < Constants.MAX_COL);
  }

  public static Position getNewPositionFromDirection(Position position, char direction) {

    return switch (direction) {

      case Constants.LEFT_MOVE -> new Position(position.getRow(), position.getCol() - 1);
      case Constants.FORWARD_MOVE -> new Position(position.getRow() - 1, position.getCol());
      case Constants.RIGHT_MOVE -> new Position(position.getRow(), position.getCol() + 1);
      case Constants.BACKWARD_MOVE -> new Position(position.getRow() + 1, position.getCol());

      default -> throw new IllegalArgumentException("Unexpected value: " + direction);
    };
  }

  public static boolean arePositionsInline(Position positionOne, Position positionTwo) {

    return (positionOne.row == positionTwo.row && positionOne.col != positionTwo.col)
        || (positionOne.row != positionTwo.row && positionOne.col == positionTwo.col);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj instanceof Position position) {
      return this.row == position.row && this.col == position.col;
    }

    return false;
  }

  @Override
  public int hashCode() {

    final int PRIME = 31;

    int result = 1;

    result = PRIME * result + row;
    result = PRIME * result + col;

    return result;
  }
}
