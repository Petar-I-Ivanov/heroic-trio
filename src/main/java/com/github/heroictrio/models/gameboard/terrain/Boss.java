package com.github.heroictrio.models.gameboard.terrain;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Boss extends GameboardObject {

  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Boss() {
    super(Constants.BOSS_SIGN);
  }
}
