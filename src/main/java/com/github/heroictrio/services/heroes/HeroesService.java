package com.github.heroictrio.services.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.repositories.GameboardObjectRepository;
import com.github.heroictrio.services.RandomGeneratorService;
import com.github.heroictrio.utilities.Constants;
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

  public void move(Long gameId, char heroPick, char direction) {

    switch (heroPick) {

      case Constants.GNOME_PICK -> gnomeService.move(gameId, direction);
      case Constants.DWARF_PICK -> dwarfService.move(gameId, direction);
      case Constants.WIZARD_PICK -> wizardService.move(gameId, direction);

      default -> throw new IllegalArgumentException("Unexpected value: " + heroPick);
    }
  }

  public void ability(Game game, char direction, byte numberOfSquares) {

    gnomeService.ability(game, direction, numberOfSquares);
    // wizardService.ability(gameId, positionOne, positionTwo, isAscending);
  }
}
