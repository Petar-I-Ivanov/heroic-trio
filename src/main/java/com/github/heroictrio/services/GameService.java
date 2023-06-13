package com.github.heroictrio.services;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.GameStatusEnum;
import com.github.heroictrio.models.gameboard.GameboardObject;
import com.github.heroictrio.repositories.GameRepository;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.heroes.HeroesService;
import com.github.heroictrio.services.terrain.TerrainService;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import com.github.heroictrio.validators.Input;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

  private GameRepository gameRepository;
  private GameboardObjectRepository goRepo;

  private HeroesService heroesService;
  private TerrainService terrainService;

  public GameService(GameRepository gameRepository, GameboardObjectRepository goRepo,
      HeroesService heroesService, TerrainService terrainService) {

    this.gameRepository = gameRepository;
    this.goRepo = goRepo;

    this.heroesService = heroesService;
    this.terrainService = terrainService;
  }

  public Game findGameById(Long gameId) {
    return gameRepository.findById(gameId);
  }

  public Game startNewGame() {

    Game game = new Game();
    gameRepository.save(game);

    heroesService.initializeHeroes(game);
    terrainService.initializeTerrain(game);

    gameRepository.save(game);
    return game;
  }

  public Game makeAction(Long gameId, Input input) {

    Game game = findGameById(gameId);
    heroesService.handleAction(game, input);

    if (isTurnOver(gameId)) {

      game.setTurn((short) (game.getTurn() + 1));
      gameRepository.save(game);
      heroesService.newTurn(gameId);
    }

    if (isGameWon(game) || isGameLost(game)) {

      GameStatusEnum status = (isGameWon(game)) ? GameStatusEnum.WON : GameStatusEnum.LOST;
      game.setStatus(status);
      gameRepository.save(game);
    }

    return game;
  }

  public boolean isGnomeUsed(Long gameId) {
    return heroesService.isGnomeUsed(gameId);
  }

  public boolean isDwarfUsed(Long gameId) {
    return heroesService.isDwarfUsed(gameId);
  }

  public boolean isWizardUsed(Long gameId) {
    return heroesService.isWizardUsed(gameId);
  }

  public String[][] getGameboard(Long gameId) {

    String[][] gameboard = new String[Constants.MAX_ROW][Constants.MAX_COL];

    for (GameboardObject obj : goRepo.findAllByGameId(gameId)) {
      gameboard[obj.getRowLocation()][obj.getColLocation()] = obj.getSign();
    }

    for (int row = 0; row < gameboard.length; row++) {
      for (int col = 0; col < gameboard[0].length; col++) {

        if (gameboard[row][col] == null) {

          Position position = new Position(row, col);
          Game game = gameRepository.findById(gameId);

          terrainService.addBackground(game, position);
          gameboard[row][col] = goRepo.findAnyByGameIdAndPosition(gameId, position).getSign();
        }
      }
    }

    return gameboard;
  }

  private boolean isTurnOver(Long gameId) {

    return heroesService.isDwarfUsed(gameId) && heroesService.isGnomeUsed(gameId)
        && heroesService.isWizardUsed(gameId);
  }

  private boolean isGameWon(Game game) {

    return !Constants.BOSS_POSITIONS.stream()
        .filter(position -> heroesService.isHeroAtPosition(game.getId(), position)).toList()
        .isEmpty();
  }

  private static boolean isGameLost(Game game) {
    return game.getTurn() > 16;
  }
}
