package com.github.heroictrio.utilities;

import java.util.Arrays;
import java.util.List;

public class Constants {

  public static final byte MAX_ROW = 12;
  public static final byte MAX_COL = 18;

  public static final String GNOME_SIGN = "G";
  public static final String DWARF_SIGN = "D";
  public static final String WIZARD_SIGN = "W";
  public static final String BOSS_SIGN = "X";
  public static final String EMPTY_CORNER_SIGN = "E";

  public static final char FORWARD_MOVE = 'w';
  public static final char RIGHT_MOVE = 'd';
  public static final char BACKWARD_MOVE = 's';
  public static final char LEFT_MOVE = 'a';

  public static final char GNOME_PICK = '1';
  public static final char DWARF_PICK = '2';
  public static final char WIZARD_PICK = '3';

  public static final List<Position> BOSS_POSITIONS =
      Arrays.asList(new Position(5, 8), new Position(5, 9), new Position(6, 8), new Position(6, 9));

  private Constants() {}
}
