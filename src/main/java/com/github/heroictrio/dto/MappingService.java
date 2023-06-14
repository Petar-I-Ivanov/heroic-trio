package com.github.heroictrio.dto;

import com.github.heroictrio.encoder.IdEncoder;
import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.GameStatusEnum;
import com.github.heroictrio.services.GameService;
import com.github.heroictrio.validators.Input;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MappingService {

  private GameService gameService;
  private IdEncoder encoder;

  public MappingService(GameService gameService, IdEncoder encoder) {
    this.gameService = gameService;
    this.encoder = encoder;
  }

  public GameDTO startNewGame() {
    return convertGameToDto(gameService.startNewGame());
  }

  public GameDTO getGameById(String id) {
    Long gameId = encoder.decode(id);
    return convertGameToDto(gameService.findGameById(gameId));
  }

  public GameDTO makeAction(String id, Input input) {
    Long gameId = encoder.decode(id);
    return convertGameToDto(gameService.makeAction(gameId, input));
  }

  private GameDTO convertGameToDto(Game game) {

    GameDTO dto = new GameDTO();

    dto.setId(encoder.encode(game.getId()));
    dto.setTurn(game.getTurn());
    dto.setStatus(game.getStatus());

    if (game.getStatus() == GameStatusEnum.ONGOING) {

      dto.setGnomeUsed(gameService.isGnomeUsed(game.getId()));
      dto.setDwarfUsed(gameService.isDwarfUsed(game.getId()));
      dto.setWizardUsed(gameService.isWizardUsed(game.getId()));
      dto.setGameboard(gameService.getGameboard(game.getId()));
    }

    return dto;
  }
}
