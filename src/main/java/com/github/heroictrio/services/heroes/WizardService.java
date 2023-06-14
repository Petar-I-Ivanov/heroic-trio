package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.terrain.TerrainService;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WizardService {

  private GameboardObjectRepository goRepo;
  private TerrainService terrainService;

  public WizardService(GameboardObjectRepository goRepo, TerrainService terrainService) {
    this.goRepo = goRepo;
    this.terrainService = terrainService;
  }

  public boolean isWizardAtPosition(Long gameId, Position position) {
    return goRepo.findByGameIdAndPosition(gameId, position, Wizard.class) != null;
  }

  public void setWizardUsed(Long gameId) {
    Wizard wizard = goRepo.findSingleByGameId(gameId, Wizard.class);
    wizard.setUsedThisTurn(true);
    goRepo.save(wizard);
  }

  public void move(Long gameId, char direction) {

    Wizard wizard = goRepo.findSingleByGameId(gameId, Wizard.class);
    HeroesService.heroUsedValidation(wizard);

    Position position = Position.getNewPositionFromDirection(wizard.getLocation(), direction);

    if (isNextPositionBackgroundAndValid(gameId, wizard.getLastValue(), position)) {

      short value = (short) terrainService.getBackgroundValueAt(gameId, position);
      terrainService.removeBackgroundAt(gameId, position);
      wizard.setLocation(position);
      wizard.setLastValue(value);
      goRepo.save(wizard);
      return;
    }

    if (terrainService.isPositionBoss(gameId, position)) {

      terrainService.removeBossAt(gameId, position);
      wizard.setLocation(position);
      goRepo.save(wizard);
      return;
    }

    throw new IllegalArgumentException("Next value isn't decreasing or boss!");
  }

  public void ability(Long gameId, Position fromPosition, Position toPosition,
      boolean isAscending) {

    Wizard wizard = goRepo.findSingleByGameId(gameId, Wizard.class);
    HeroesService.heroUsedValidation(wizard);

    if (Position.arePositionsInline(fromPosition, toPosition)) {

      boolean arePositionsSwitched = false;

      if (shouldPositionsSwitch(fromPosition, toPosition)) {

        Position temp = fromPosition;
        fromPosition = toPosition;
        toPosition = temp;

        arePositionsSwitched = true;
      }

      terrainService.orderBackgrounds(gameId, fromPosition, toPosition, isAscending,
          arePositionsSwitched);
      return;
    }

    throw new IllegalArgumentException("Two positions aren't inline or correct!");
  }

  private boolean isNextPositionBackgroundAndValid(Long gameId, int wizardLastValue,
      Position nextPosition) {

    int nextValue = terrainService.getBackgroundValueAt(gameId, nextPosition);

    return terrainService.isPositionBackground(gameId, nextPosition)
        && isDecreasingNumber(wizardLastValue, nextValue);
  }

  private static boolean isDecreasingNumber(int wizardLastValue, int nextValue) {
    return nextValue < wizardLastValue;
  }

  private static boolean shouldPositionsSwitch(Position one, Position two) {
    return (one.getRow() > two.getRow()) || one.getCol() > two.getCol();
  }
}
