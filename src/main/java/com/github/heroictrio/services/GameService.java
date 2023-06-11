package com.github.heroictrio.services;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import com.github.heroictrio.repositories.GameRepository;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.heroes.HeroesService;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
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

  public Game makeAction(Long gameId, char heroPick, char direction) {

    Game game = findGameById(gameId);

    heroesService.ability(game, direction, (byte) 2);
    // heroesService.ability(gameId, new Position(0, 8), new Position(11, 8), false);
    // heroesService.move(gameId, heroPick, direction);
    return game;
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

          terrainService.addBackgroundAt(game, position);
          gameboard[row][col] = goRepo.findAnyByGameIdAndPosition(gameId, position).getSign();
        }
      }
    }

    return gameboard;
  }
}