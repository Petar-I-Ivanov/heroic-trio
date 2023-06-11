package com.github.heroictrio.models.gameboard.terrain;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Background extends GameboardObject {

  @ManyToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Background() {
    super(null);
  }

  public Background(int value) {
    super(String.valueOf(value));
  }

  public void setValue(int value) {
    this.sign = String.valueOf(value);
  }

  public int getValue() {
    return Integer.parseInt(this.sign);
  }
}
