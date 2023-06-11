package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.PositionCheckService;
import com.github.heroictrio.services.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WizardService {

  private GameboardObjectRepository goRepo;
  private PositionCheckService positionService;
  private TerrainService terrainService;

  public WizardService(GameboardObjectRepository goRepo, PositionCheckService positionCheckService,
      TerrainService terrainService) {

    this.goRepo = goRepo;
    this.positionService = positionCheckService;
    this.terrainService = terrainService;
  }

  public void move(Long gameId, char direction) {

    Wizard wizard = goRepo.findSingleByGameId(gameId, Wizard.class);
    Position position = Position.getNewPositionFromDirection(wizard.getLocation(), direction);

    if (isNextPositionInvalid(gameId, wizard.getLastValue(), position)) {
      throw new IllegalArgumentException("Next value isn't proggresive even!");
    }

    terrainService.deleteTerrainAt(gameId, position);
    wizard.setLocation(position);
    goRepo.save(wizard);
  }

  public void ability(Long gameId, Position fromPosition, Position toPosition,
      boolean isAscending) {

    if (!Position.arePositionsInline(fromPosition, toPosition)) {
      throw new IllegalArgumentException("Two positions aren't inline!");
    }

    if ((fromPosition.getRow() > toPosition.getRow())
        || fromPosition.getCol() > toPosition.getCol()) {

      Position temp = fromPosition;
      fromPosition = toPosition;
      toPosition = temp;
    }

    terrainService.orderBackgroundsFromToPosition(gameId, fromPosition, toPosition, isAscending);
  }

  private boolean isNextPositionInvalid(Long gameId, int wizardLastValue, Position nextPosition) {

    int nextValue = positionService.getPositionValue(gameId, nextPosition);
    return nextValue == -1 || !isDecraciveNumber(wizardLastValue, nextValue);
  }

  private static boolean isDecraciveNumber(int wizardLastValue, int nextValue) {
    return nextValue < wizardLastValue;
  }
}
