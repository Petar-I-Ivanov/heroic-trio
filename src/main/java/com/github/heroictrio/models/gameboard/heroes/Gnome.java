package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Gnome extends GameboardObject {

  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Gnome() {
    super(Constants.GNOME_SIGN);
  }
}
