package com.github.heroictrio.services;

import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class RandomGeneratorService {

  private final Random RANDOM;
  private GameboardObjectRepository goRepo;

  public RandomGeneratorService(GameboardObjectRepository goRepo) {
    this.RANDOM = new Random();
    this.goRepo = goRepo;
  }

  public Position getRandomFreeCorner(Long gameId) {

    List<Position> filteredPositions =
        getCornerPositions().stream().filter(position -> isPositionFree(gameId, position)).toList();

    return getRandomPositionFromList(filteredPositions);
  }

  private Position getRandomPositionFromList(List<Position> positions) {

    if (positions.isEmpty()) {
      return null;
    }

    int listSize = positions.size();
    int randomIndex = RANDOM.nextInt(listSize);

    return positions.get(randomIndex);
  }

  private static List<Position> getCornerPositions() {

    int maxRow = Constants.MAX_ROW - 1;
    int maxCol = Constants.MAX_COL - 1;

    return Arrays.asList(new Position(0, 0), new Position(0, maxCol), new Position(maxRow, 0),
        new Position(maxRow, maxCol));
  }

  private boolean isPositionFree(Long gameId, Position position) {
    return goRepo.findAnyByGameIdAndPosition(gameId, position) == null;
  }
}
