package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.PositionCheckService;
import com.github.heroictrio.services.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GnomeService {

  private GameboardObjectRepository goRepo;
  private PositionCheckService positionService;
  private TerrainService terrainService;

  public GnomeService(GameboardObjectRepository goRepo, PositionCheckService positionService,
      TerrainService terrainService) {

    this.goRepo = goRepo;
    this.positionService = positionService;
    this.terrainService = terrainService;
  }

  public void setGnomeUsed(Long gameId) {
    Gnome gnome = goRepo.findSingleByGameId(gameId, Gnome.class);
    gnome.setUsedThisTurn(true);
    goRepo.save(gnome);
  }

  public void move(Long gameId, char direction) {

    Gnome gnome = goRepo.findSingleByGameId(gameId, Gnome.class);

    if (gnome.isUsedThisTurn()) {
      throw new IllegalArgumentException("This unit is used already!");
    }

    Position position = Position.getNewPositionFromDirection(gnome.getLocation(), direction);

    if (isNextPositionInvalid(gameId, position)) {
      throw new IllegalArgumentException("Next value isn't higher than around's average!");
    }

    terrainService.deleteTerrainAt(gameId, position);
    gnome.setLocation(position);
    goRepo.save(gnome);
  }

  public void ability(Game game, char direction, byte numberOfSquares) {

    Long gameId = game.getId();
    Gnome gnome = goRepo.findSingleByGameId(gameId, Gnome.class);

    if (gnome.isUsedThisTurn()) {
      throw new IllegalArgumentException("This unit is used already!");
    }

    if (numberOfSquares != 2 && numberOfSquares != 3) {
      throw new IllegalArgumentException("Invalid value for numberOfSquares: " + numberOfSquares);
    }

    Position gnomePosition = gnome.getLocation();
    int sum = getSum(gameId, gnomePosition, direction, numberOfSquares);

    if (sum == -1) {
      throw new IllegalArgumentException("No backgrounds in picked direction!");
    }

    Position nextPosition = Position.getNewPositionFromDirection(gnomePosition, direction);
    terrainService.setValueAtBackground(game, nextPosition, sum);

  }

  private boolean isNextPositionInvalid(Long gameId, Position nextPosition) {

    int nextValue = positionService.getPositionValue(gameId, nextPosition);
    return nextValue == -1 || !isAroundAvgLower(gameId, nextPosition);
  }

  private boolean isAroundAvgLower(Long gameId, Position nextPosition) {

    int valueToCheck = positionService.getPositionValue(gameId, nextPosition);

    int sum = 0;
    int counter = 0;

    int startRow = nextPosition.getRow() - 1;
    int startCol = nextPosition.getCol() - 1;

    int endRow = nextPosition.getRow() + 1;
    int endCol = nextPosition.getCol() + 1;

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {

        Position position = new Position(row, col);
        int value = positionService.getPositionValue(gameId, position);

        if (value != -1 && value != valueToCheck) {

          sum += value;
          counter++;
        }
      }
    }

    return (sum / counter) < valueToCheck;
  }

  private int getSum(Long gameId, Position gnomePosition, char direction, byte numberOfSquares) {

    Position nextPosition = Position.getNewPositionFromDirection(gnomePosition, direction);

    int sum = 0;

    for (int i = 0; i < numberOfSquares; i++) {

      int value = terrainService.getValueAt(gameId, nextPosition);

      if (value != -1) {
        sum += value;
        terrainService.deleteTerrainAt(gameId, nextPosition);
        nextPosition = Position.getNewPositionFromDirection(nextPosition, direction);
      }
    }

    return (sum != 0) ? sum : -1;
  }
}
