package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Wizard extends GameboardObject {

  @Column(name = "last_value")
  private short lastValue;

  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Wizard() {
    super(Constants.WIZARD_SIGN);
    this.lastValue = Short.MAX_VALUE;
  }
}