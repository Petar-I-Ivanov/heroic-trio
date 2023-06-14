package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.models.gameboard.heroes.Hero;
import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.RandomGeneratorService;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import com.github.heroictrio.validators.Input;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class HeroesService {

  private GameboardObjectRepository goRepo;
  private RandomGeneratorService randomService;

  private DwarfService dwarfService;
  private GnomeService gnomeService;
  private WizardService wizardService;

  public HeroesService(GameboardObjectRepository goRepo, RandomGeneratorService randomService,
      DwarfService dwarfService, GnomeService gnomeService, WizardService wizardService) {

    this.goRepo = goRepo;
    this.randomService = randomService;

    this.dwarfService = dwarfService;
    this.gnomeService = gnomeService;
    this.wizardService = wizardService;
  }

  public boolean isHeroAtPosition(Long gameId, Position position) {

    return dwarfService.isDwarfAtPosition(gameId, position)
        || gnomeService.isGnomeAtPosition(gameId, position)
        || wizardService.isWizardAtPosition(gameId, position);
  }

  public boolean isGnomeUsed(Long gameId) {
    return goRepo.findSingleByGameId(gameId, Gnome.class).isUsedThisTurn();
  }

  public boolean isDwarfUsed(Long gameId) {
    return goRepo.findSingleByGameId(gameId, Dwarf.class).isUsedThisTurn();
  }

  public boolean isWizardUsed(Long gameId) {
    return goRepo.findSingleByGameId(gameId, Wizard.class).isUsedThisTurn();
  }

  public void newTurn(Long gameId) {

    Gnome gnome = goRepo.findSingleByGameId(gameId, Gnome.class);
    Dwarf dwarf = goRepo.findSingleByGameId(gameId, Dwarf.class);
    Wizard wizard = goRepo.findSingleByGameId(gameId, Wizard.class);

    gnome.setUsedThisTurn(false);
    dwarf.setUsedThisTurn(false);
    wizard.setUsedThisTurn(false);

    goRepo.save(List.of(gnome, dwarf, wizard));
  }

  public void initializeHeroes(Game game) {

    Gnome gnome = new Gnome();
    gnome.setLocation(randomService.getRandomFreeCorner(game.getId()));
    gnome.setGame(game);
    goRepo.save(gnome);

    Dwarf dwarf = new Dwarf();
    dwarf.setLocation(randomService.getRandomFreeCorner(game.getId()));
    dwarf.setGame(game);
    goRepo.save(dwarf);

    Wizard wizard = new Wizard();
    wizard.setLocation(randomService.getRandomFreeCorner(game.getId()));
    wizard.setGame(game);
    goRepo.save(wizard);

    game.setGnome(gnome);
    game.setDwarf(dwarf);
    game.setWizard(wizard);
  }

  public void handleAction(Game game, Input input) {

    boolean isMovement = input.getAction().equals(Constants.MOVEMENT);

    if (isMovement) {
      move(game.getId(), input);
      return;
    }

    ability(game, input);
  }

  public static void heroUsedValidation(Hero hero) {

    if (hero.isUsedThisTurn()) {
      throw new IllegalArgumentException("This hero is used already this turn!");
    }
  }

  private void move(Long gameId, Input input) {

    char heroPick = input.getHeroPick().charAt(0);
    char direction = input.getDirection().charAt(0);

    switch (heroPick) {

      case Constants.GNOME_PICK -> {
        gnomeService.move(gameId, direction);
        gnomeService.setGnomeUsed(gameId);
      }
      case Constants.DWARF_PICK -> {
        dwarfService.move(gameId, direction);
        dwarfService.setDwarfUsed(gameId);
      }
      case Constants.WIZARD_PICK -> {
        wizardService.move(gameId, direction);
        wizardService.setWizardUsed(gameId);
      }

      default -> throw new IllegalArgumentException("Unexpected value for heroPick: " + heroPick);
    }
  }

  private void ability(Game game, Input input) {

    char heroPick = input.getHeroPick().charAt(0);

    switch (heroPick) {

      case Constants.GNOME_PICK -> {

        char direction = input.getDirection().charAt(0);
        byte numberOfSquares = input.getNumberOfSquares();

        gnomeService.ability(game, direction, numberOfSquares);
        gnomeService.setGnomeUsed(game.getId());
      }

      case Constants.DWARF_PICK -> {

        Position positionOne = new Position(input.getRowOne(), input.getColOne());
        Position positionTwo = new Position(input.getRowTwo(), input.getColTwo());

        dwarfService.ability(game.getId(), positionOne, positionTwo);
        dwarfService.setDwarfUsed(game.getId());
      }

      case Constants.WIZARD_PICK -> {

        Position positionOne = new Position(input.getRowOne(), input.getColOne());
        Position positionTwo = new Position(input.getRowTwo(), input.getColTwo());
        boolean isAscending = input.getSortType().equals("ascending");

        wizardService.ability(game.getId(), positionOne, positionTwo, isAscending);
        wizardService.setWizardUsed(game.getId());
      }

      default -> throw new IllegalArgumentException("Unexpected value for heroPick: " + heroPick);
    }
  }
}
