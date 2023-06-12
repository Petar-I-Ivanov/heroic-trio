package com.github.heroictrio.services.terrain;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.terrain.Boss;
import com.github.heroictrio.models.gameboard.terrain.EmptyCorner;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.RandomGeneratorService;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TerrainService {

  private GameboardObjectRepository goRepo;

  private RandomGeneratorService randomService;
  private BackgroundService backgroundService;

  public TerrainService(GameboardObjectRepository goRepo, RandomGeneratorService randomService,
      BackgroundService backgroundService) {

    this.goRepo = goRepo;
    this.randomService = randomService;
    this.backgroundService = backgroundService;
  }

  public void initializeTerrain(Game game) {

    initializeEmptyCorner(game);
    initializeBoss(game);
    backgroundService.initializeBackground(game);
  }

  public boolean isPositionBoss(Long gameId, Position position) {
    return goRepo.findByGameIdAndPosition(gameId, position, Boss.class) != null;
  }

  public boolean isPositionBackground(Long gameId, Position position) {
    return backgroundService.isPositionBackground(gameId, position);
  }

  public int getBackgroundValueAt(Long gameId, Position position) {
    return backgroundService.getBackgroundValueAt(gameId, position);
  }

  public void addBackground(Game game, Position position) {
    backgroundService.addBackground(game, position);
  }

  public void addBackground(Game game, Position position, int value) {
    backgroundService.addBackground(game, position, value);
  }

  public void switchTwoBackgrounds(Long gameId, Position positionOne, Position positionTwo) {
    backgroundService.switchTwoBackgrounds(gameId, positionOne, positionTwo);
  }

  public void orderBackgrounds(Long gameId, Position from, Position to, boolean isAscending) {
    backgroundService.orderBackgrounds(gameId, from, to, isAscending);
  }

  public void removeBackgroundAt(Long gameId, Position position) {
    backgroundService.removeBackgroundAt(gameId, position);
  }

  public void removeBossAt(Long gameId, Position position) {

    Boss boss = goRepo.findByGameIdAndPosition(gameId, position, Boss.class);

    if (boss != null) {
      goRepo.delete(boss);
    }
  }

  private void initializeEmptyCorner(Game game) {

    EmptyCorner emptyCorner = new EmptyCorner();
    emptyCorner.setLocation(randomService.getRandomFreeCorner(game.getId()));
    emptyCorner.setGame(game);
    goRepo.save(emptyCorner);
    game.setEmptyCorner(emptyCorner);
  }

  private void initializeBoss(Game game) {

    for (Position position : Constants.BOSS_POSITIONS) {

      Boss boss = new Boss();
      boss.setLocation(position);
      boss.setGame(game);
      goRepo.save(boss);
    }
  }
}
