package com.github.heroictrio.services;

import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class RandomGeneratorService {

  private final Random RANDOM;
  private PositionCheckService positionService;

  public RandomGeneratorService(PositionCheckService positionService) {

    this.RANDOM = new Random();
    this.positionService = positionService;
  }

  public Position getRandomFreeCorner(Long gameId) {

    List<Position> filteredPositions = getCornerPositions().stream()
        .filter(position -> positionService.isPositionFree(gameId, position)).toList();

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
}
