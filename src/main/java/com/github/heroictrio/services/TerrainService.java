package com.github.heroictrio.services;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.terrain.Background;
import com.github.heroictrio.models.gameboard.terrain.Boss;
import com.github.heroictrio.models.gameboard.terrain.EmptyCorner;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.repositories.LastBackgroundRepository;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TerrainService {

  private GameboardObjectRepository goRepo;
  private LastBackgroundRepository lastBackgroundRepository;

  private RandomGeneratorService randomService;
  private PositionCheckService positionService;

  public TerrainService(GameboardObjectRepository goRepo,
      LastBackgroundRepository lastBackgroundRepository, RandomGeneratorService randomService,
      PositionCheckService positionService) {

    this.goRepo = goRepo;
    this.lastBackgroundRepository = lastBackgroundRepository;

    this.randomService = randomService;
    this.positionService = positionService;
  }

  public void initializeTerrain(Game game) {
    initializeEmptyCorner(game);
    initializeBoss(game);
    initializeBackground(game);
  }

  public int getValueAt(Long gameId, Position position) {

    Background background = goRepo.findByGameIdAndPosition(gameId, position, Background.class);

    if (background != null) {
      return background.getValue();
    }

    return -1;
  }

  // TODO: Simplify
  public void orderBackgroundsFromToPosition(Long gameId, Position fromPosition,
      Position toPosition, boolean isAscending) {

    List<Background> backgrounds =
        getBackgroundsFromPositionToPosition(gameId, fromPosition, toPosition);

    for (int i = 0; i < backgrounds.size(); i++) {
      for (int j = 0; j < backgrounds.size() - 1 - i; j++) {

        Background leftBackground = backgrounds.get(j);
        Background rightBackground = backgrounds.get(j + 1);

        boolean isSwapping =
            (isAscending) ? (leftBackground.getValue() > rightBackground.getValue())
                : (leftBackground.getValue() < rightBackground.getValue());

        if (isSwapping) {

          Position temp = leftBackground.getLocation();
          leftBackground.setLocation(rightBackground.getLocation());
          rightBackground.setLocation(temp);
        }
      }
    }

    goRepo.save(backgrounds);
  }

  public void addBackgroundAt(Game game, Position position) {

    Background highestBackground = lastBackgroundRepository.getHighestBackground(game.getId());
    int highestValue = highestBackground.getValue() + 1;

    Background background = new Background(highestValue);
    background.setLocation(position);
    background.setGame(game);
    goRepo.save(background);
  }

  public void switchBackgroundsAt(Long gameId, Position positionOne, Position positionTwo) {

    Background backgroundOne =
        goRepo.findByGameIdAndPosition(gameId, positionOne, Background.class);

    Background backgroundTwo =
        goRepo.findByGameIdAndPosition(gameId, positionTwo, Background.class);

    backgroundOne.setLocation(positionTwo);
    backgroundTwo.setLocation(positionOne);

    goRepo.save(List.of(backgroundOne, backgroundTwo));
  }

  public void setValueAtBackground(Game game, Position position, int value) {

    Background background =
        goRepo.findByGameIdAndPosition(game.getId(), position, Background.class);

    if (background != null) {
      background.setValue(value);
      goRepo.save(background);
      return;
    }

    background = new Background(value);
    background.setLocation(position);
    background.setGame(game);
    goRepo.save(background);
  }

  public void deleteTerrainAt(Long gameId, Position position) {

    Background background = goRepo.findByGameIdAndPosition(gameId, position, Background.class);

    if (background != null) {
      goRepo.delete(background);
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

  private void initializeBackground(Game game) {

    int counter = 1;

    for (int row = 0; row < Constants.MAX_ROW; row++) {
      for (int col = 0; col < Constants.MAX_COL; col++) {

        Position position = new Position(row, col);

        if (positionService.isPositionFree(game.getId(), position)) {

          Background background = new Background(counter);
          background.setLocation(position);
          background.setGame(game);

          counter++;
          goRepo.save(background);
        }
      }
    }
  }

  // TODO: replace this function inside the repository with BETWEEN operation
  private List<Background> getBackgroundsFromPositionToPosition(Long gameId, Position from,
      Position to) {

    List<Background> backgrounds = new ArrayList<>();

    for (int row = from.getRow(); row <= to.getRow(); row++) {
      for (int col = from.getCol(); col <= to.getCol(); col++) {

        Background background =
            goRepo.findByGameIdAndPosition(gameId, new Position(row, col), Background.class);

        if (background != null) {
          backgrounds.add(background);
        }
      }
    }

    return backgrounds;
  }
}
