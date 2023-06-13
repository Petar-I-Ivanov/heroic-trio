package com.github.heroictrio.services.terrain;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.terrain.Background;
import com.github.heroictrio.repositories.AdditionalBackgroundRepository;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BackgroundService {

  private GameboardObjectRepository goRepo;
  private AdditionalBackgroundRepository backgroundRepository;

  public BackgroundService(GameboardObjectRepository goRepo,
      AdditionalBackgroundRepository backgroundRepository) {

    this.goRepo = goRepo;
    this.backgroundRepository = backgroundRepository;
  }

  public boolean isPositionBackground(Long gameId, Position position) {
    return goRepo.findByGameIdAndPosition(gameId, position, Background.class) != null;
  }

  public int getBackgroundValueAt(Long gameId, Position position) {

    Background background = goRepo.findByGameIdAndPosition(gameId, position, Background.class);

    if (background != null) {
      return background.getValue();
    }

    return 0;
  }

  public void initializeBackground(Game game) {

    List<Background> backgrounds = new ArrayList<>();
    int counter = 1;

    for (int row = 0; row < Constants.MAX_ROW; row++) {
      for (int col = 0; col < Constants.MAX_COL; col++) {

        Position position = new Position(row, col);

        if (isPositionFree(game.getId(), position)) {
          backgrounds.add(new Background(counter++, position, game));
        }
      }
    }

    goRepo.save(backgrounds);
  }

  public void addBackground(Game game, Position position) {

    if (isPositionFree(game.getId(), position)) {

      int highestValue = backgroundRepository.getHighestBackground(game.getId()).getValue();

      Background background = new Background(highestValue + 1, position, game);
      goRepo.save(background);
    }
  }

  public void addBackground(Game game, Position position, int value) {

    if (isPositionFree(game.getId(), position)) {

      Background background = new Background(value, position, game);
      goRepo.save(background);
    }
  }

  public void switchTwoBackgrounds(Long gameId, Position positionOne, Position positionTwo) {

    Background bgOne = goRepo.findByGameIdAndPosition(gameId, positionOne, Background.class);
    Background bgTwo = goRepo.findByGameIdAndPosition(gameId, positionTwo, Background.class);

    if (bgOne != null && bgTwo != null) {

      bgOne.setLocation(positionTwo);
      bgTwo.setLocation(positionOne);

      goRepo.save(List.of(bgOne, bgTwo));
    }
  }

  public void orderBackgrounds(Long gameId, Position from, Position to, boolean isAscending) {

    List<Background> backgrounds = backgroundRepository.getBackgroundsBetween(gameId, from, to);
    List<Position> positions =
        backgrounds.stream().map(Background::getLocation).collect(Collectors.toList());
    positions.sort(Comparator.comparing(Position::getRow).thenComparing(Position::getCol));

    if (isAscending) {
      backgrounds.sort(Comparator.comparing(Background::getValue));
    } else {
      backgrounds.sort(Comparator.comparing(Background::getValue).reversed());
    }

    for (int i = 0; i < backgrounds.size(); i++) {
      backgrounds.get(i).setLocation(positions.get(i));
    }

    goRepo.save(backgrounds);
  }

  public void removeBackgroundAt(Long gameId, Position position) {

    Background background = goRepo.findByGameIdAndPosition(gameId, position, Background.class);

    if (background != null) {
      goRepo.delete(background);
    }
  }

  private boolean isPositionFree(Long gameId, Position position) {
    return goRepo.findAnyByGameIdAndPosition(gameId, position) == null;
  }
}
