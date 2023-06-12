package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.terrain.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GnomeService {

  private GameboardObjectRepository goRepo;
  private TerrainService terrainService;

  public GnomeService(GameboardObjectRepository goRepo, TerrainService terrainService) {
    this.goRepo = goRepo;
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

    if (isNextPositionBackgroundAndValid(gameId, position)) {

      terrainService.removeBackgroundAt(gameId, position);
      gnome.setLocation(position);
      goRepo.save(gnome);
      return;
    }

    if (terrainService.isPositionBoss(gameId, position)) {

      terrainService.removeBossAt(gameId, position);
      gnome.setLocation(position);
      goRepo.save(gnome);
      return;
    }

    throw new IllegalArgumentException("Next value isn't higher than around's average!");
  }

  public void ability(Game game, char direction, byte numberOfSquares) {

    Long gameId = game.getId();
    Gnome gnome = goRepo.findSingleByGameId(gameId, Gnome.class);

    if (gnome.isUsedThisTurn()) {
      throw new IllegalArgumentException("This unit is used already!");
    }

    Position gnomePosition = gnome.getLocation();
    int sum = getSum(gameId, gnomePosition, direction, numberOfSquares);

    if (sum != 0) {

      Position nextPosition = Position.getNewPositionFromDirection(gnomePosition, direction);
      terrainService.addBackground(game, nextPosition, sum);
      return;
    }

    throw new IllegalArgumentException("No backgrounds in picked direction!");
  }

  private boolean isNextPositionBackgroundAndValid(Long gameId, Position nextPosition) {

    return terrainService.isPositionBackground(gameId, nextPosition)
        && isAroundAvgLower(gameId, nextPosition);
  }

  private boolean isAroundAvgLower(Long gameId, Position nextPosition) {

    int valueToCheck = terrainService.getBackgroundValueAt(gameId, nextPosition);

    int sum = 0;
    int counter = 0;

    int startRow = nextPosition.getRow() - 1;
    int startCol = nextPosition.getCol() - 1;

    int endRow = nextPosition.getRow() + 1;
    int endCol = nextPosition.getCol() + 1;

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {

        Position position = new Position(row, col);

        if (terrainService.isPositionBackground(gameId, position)) {

          int value = terrainService.getBackgroundValueAt(gameId, position);

          if (value != valueToCheck) {

            sum += value;
            counter++;
          }
        }
      }
    }

    return (sum / counter) < valueToCheck;
  }

  private int getSum(Long gameId, Position gnomePosition, char direction, byte numberOfSquares) {

    Position nextPosition = Position.getNewPositionFromDirection(gnomePosition, direction);

    int sum = 0;

    for (int i = 0; i < numberOfSquares; i++) {

      if (terrainService.isPositionBackground(gameId, nextPosition)) {

        sum += terrainService.getBackgroundValueAt(gameId, nextPosition);
        terrainService.removeBackgroundAt(gameId, nextPosition);
        nextPosition = Position.getNewPositionFromDirection(nextPosition, direction);
      }
    }

    return sum;
  }
}
