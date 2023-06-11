package com.github.heroictrio.dto;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.services.GameService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MappingService {

  private GameService gameService;

  public MappingService(GameService gameService) {
    this.gameService = gameService;
  }

  public GameDTO startNewGame() {
    return convertGameToDto(gameService.startNewGame());
  }

  public GameDTO getGameById(Long gameId) {
    return convertGameToDto(gameService.findGameById(gameId));
  }

  public GameDTO makeAction(Long gameId, char heroPick, char direction) {
    return convertGameToDto(gameService.makeAction(gameId, heroPick, direction));
  }

  private GameDTO convertGameToDto(Game game) {

    GameDTO dto = new GameDTO();

    dto.setId(game.getId());
    dto.setTurn(game.getTurn());
    dto.setStatus(game.getStatus());

    dto.setGameboard(gameService.getGameboard(game.getId()));

    return dto;
  }
}
