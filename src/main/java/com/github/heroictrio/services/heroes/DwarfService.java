package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.PositionCheckService;
import com.github.heroictrio.services.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DwarfService {

  private GameboardObjectRepository goRepo;
  private PositionCheckService positionService;
  private TerrainService terrainService;

  public DwarfService(GameboardObjectRepository goRepo, PositionCheckService positionCheckService,
      TerrainService terrainService) {

    this.goRepo = goRepo;
    this.positionService = positionCheckService;
    this.terrainService = terrainService;
  }

  public void move(Long gameId, char direction) {

    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);
    Position position = Position.getNewPositionFromDirection(dwarf.getLocation(), direction);

    if (isNextPositionInvalid(gameId, dwarf.getLastValue(), position)) {
      throw new IllegalArgumentException("Next value isn't proggresive even!");
    }

    terrainService.deleteTerrainAt(gameId, position);
    dwarf.setLocation(position);
    goRepo.save(dwarf);
  }

  public void ability(Long gameId, Position positionOne, Position positionTwo) {

    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);

    List<Position> workingField = getWorkingField(dwarf, positionOne);

    if (workingField != null && workingField.contains(positionTwo)) {
      terrainService.switchBackgroundsAt(gameId, positionOne, positionTwo);
      return;
    }

    throw new IllegalArgumentException("Invalid positions for dwarf ability");
  }

  private boolean isNextPositionInvalid(Long gameId, int dwarfLastValue, Position nextPosition) {

    int nextValue = positionService.getPositionValue(gameId, nextPosition);
    return nextValue == -1 || !isProggressiveEvenNumber(dwarfLastValue, nextValue);
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

        if (terrainService.getValueAt(gameId, position) != -1) {
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
