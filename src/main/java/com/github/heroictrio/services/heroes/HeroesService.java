package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.RandomGeneratorService;
import com.github.heroictrio.utilities.Constants;
import com.github.heroictrio.utilities.Position;
import com.github.heroictrio.validators.Input;
import jakarta.enterprise.context.ApplicationScoped;

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

    boolean isMovement = input.getAction().equals("movemenet");

    if (isMovement) {
      move(game.getId(), input);
      return;
    }

    ability(game, input);
  }

  private void move(Long gameId, Input input) {

    char heroPick = input.getHeroPick().charAt(0);
    char direction = input.getDirection().charAt(0);

    switch (heroPick) {

      case Constants.GNOME_PICK -> gnomeService.move(gameId, direction);
      case Constants.DWARF_PICK -> dwarfService.move(gameId, direction);
      case Constants.WIZARD_PICK -> wizardService.move(gameId, direction);

      default -> throw new IllegalArgumentException("Unexpected value: " + heroPick);
    }
  }

  private void ability(Game game, Input input) {

    char heroPick = input.getHeroPick().charAt(0);

    switch (heroPick) {

      case Constants.GNOME_PICK: {

        char direction = input.getDirection().charAt(0);
        byte numberOfSquares = input.getNumberOfSquares();

        gnomeService.ability(game, direction, numberOfSquares);
        break;
      }

      case Constants.DWARF_PICK: {

        Position positionOne = new Position(input.getRowOne(), input.getColOne());
        Position positionTwo = new Position(input.getRowTwo(), input.getColTwo());

        dwarfService.ability(game.getId(), positionOne, positionTwo);
        break;
      }

      case Constants.WIZARD_PICK: {

        Position positionOne = new Position(input.getRowOne(), input.getColOne());
        Position positionTwo = new Position(input.getRowTwo(), input.getColTwo());
        boolean isAscending = input.getSortType().equals("ascending");

        wizardService.ability(game.getId(), positionOne, positionTwo, isAscending);
        break;
      }

      default:
        throw new IllegalArgumentException("Unexpected value: " + heroPick);
    }
  }
}
