package com.github.heroictrio.models.gameboard.terrain;

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
public class EmptyCorner extends GameboardObject {

  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public EmptyCorner() {
    super(Constants.EMPTY_CORNER_SIGN);
  }
}
