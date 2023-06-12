package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.terrain.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DwarfService {

  private GameboardObjectRepository goRepo;
  private TerrainService terrainService;

  public DwarfService(GameboardObjectRepository goRepo, TerrainService terrainService) {

    this.goRepo = goRepo;
    this.terrainService = terrainService;
  }

  public void setDwarfUsed(Long gameId) {
    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);
    dwarf.setUsedThisTurn(true);
    goRepo.save(dwarf);
  }

  public void move(Long gameId, char direction) {

    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);

    if (dwarf.isUsedThisTurn()) {
      throw new IllegalArgumentException("This unit is used already!");
    }

    Position position = Position.getNewPositionFromDirection(dwarf.getLocation(), direction);

    if (isNextPositionBackgroundAndValid(gameId, dwarf.getLastValue(), position)) {

      terrainService.removeBackgroundAt(gameId, position);
      dwarf.setLocation(position);
      goRepo.save(dwarf);
      return;
    }

    if (terrainService.isPositionBoss(gameId, position)) {

      terrainService.removeBossAt(gameId, position);
      dwarf.setLocation(position);
      goRepo.save(dwarf);
      return;
    }

    throw new IllegalArgumentException("Next value isn't proggresive even or boss!");
  }

  public void ability(Long gameId, Position positionOne, Position positionTwo) {

    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);

    if (dwarf.isUsedThisTurn()) {
      throw new IllegalArgumentException("This unit is used already!");
    }

    List<Position> workingField = getWorkingField(dwarf, positionOne);

    if (workingField != null && workingField.contains(positionTwo)) {
      terrainService.switchTwoBackgrounds(gameId, positionOne, positionTwo);
      return;
    }

    throw new IllegalArgumentException("Invalid positions for dwarf ability");
  }

  private boolean isNextPositionBackgroundAndValid(Long gameId, int dwarfLastValue,
      Position nextPosition) {

    int nextValue = terrainService.getBackgroundValueAt(gameId, nextPosition);

    return terrainService.isPositionBackground(gameId, nextPosition)
        && isProggressiveEvenNumber(dwarfLastValue, nextValue);
  }

  private List<Position> getWorkingField(Dwarf dwarf, Position position) {

    Long gameId = dwarf.getGame().getId();
    Position dwarfPosition = dwarf.getLocation();

    List<Position> workingField = getRightFieldValues(gameId, dwarfPosition);

    if (workingField.contains(position)) {
      return workingField;
    }

    workingField = getBottomFieldValues(gameId, dwarfPosition);

    if (workingField.contains(position)) {
      return workingField;
    }

    workingField = getLeftFieldValues(gameId, dwarfPosition);

    if (workingField.contains(position)) {
      return workingField;
    }

    workingField = getTopFieldValues(gameId, dwarfPosition);

    if (workingField.contains(position)) {
      return workingField;
    }

    return null;
  }

  private List<Position> getRightFieldValues(Long gameId, Position dwarfPosition) {
    return squaresFinder(gameId,
        new Position(dwarfPosition.getRow() - 3, dwarfPosition.getCol() + 1));
  }

  private List<Position> getBottomFieldValues(Long gameId, Position dwarfPosition) {
    return squaresFinder(gameId, new Position(dwarfPosition.getRow() + 1, dwarfPosition.getCol()));
  }

  private List<Position> getLeftFieldValues(Long gameId, Position dwarfPosition) {
    return squaresFinder(gameId, new Position(dwarfPosition.getRow(), dwarfPosition.getCol() - 4));
  }

  private List<Position> getTopFieldValues(Long gameId, Position dwarfPosition) {
    return squaresFinder(gameId,
        new Position(dwarfPosition.getRow() - 4, dwarfPosition.getCol() - 3));
  }

  private List<Position> squaresFinder(Long gameId, Position startingPosition) {

    List<Position> squareValues = new ArrayList<Position>();

    for (int row = startingPosition.getRow(); row < startingPosition.getRow() + 4; row++) {
      for (int col = startingPosition.getCol(); col < startingPosition.getCol() + 4; col++) {

        Position position = new Position(row, col);

        if (terrainService.isPositionBackground(gameId, startingPosition)) {
          squareValues.add(position);
        }
      }
    }

    return squareValues;
  }

  private static boolean isProggressiveEvenNumber(int dwarfValue, int nextValue) {
    return nextValue % 2 == 0 && nextValue > dwarfValue;
  }
}
