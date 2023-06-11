package com.github.heroictrio.services;

import com.github.heroictrio.models.gameboard.terrain.Background;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PositionCheckService {

  private GameboardObjectRepository goRepo;

  public PositionCheckService(GameboardObjectRepository goRepo) {
    this.goRepo = goRepo;
  }

  public boolean isPositionFree(Long gameId, Position position) {
    return goRepo.findAnyByGameIdAndPosition(gameId, position) == null;
  }

  public boolean isPositionBackground(Long gameId, Position position) {
    return goRepo.findByGameIdAndPosition(gameId, position, Background.class) != null;
  }

  public int getPositionValue(Long gameId, Position position) {

    Background background = goRepo.findByGameIdAndPosition(gameId, position, Background.class);

    if (background != null) {
      return background.getValue();
    }

    return -1;
  }
}
