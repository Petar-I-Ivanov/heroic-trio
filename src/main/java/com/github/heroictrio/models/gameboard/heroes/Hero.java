package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.models.Game;
import com.github.heroictrio.models.gameboard.GameboardObject;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public class Hero extends GameboardObject {

  @Column(name = "is_used_this_turn", nullable = false)
  protected boolean isUsedThisTurn;

  @OneToOne
  @JoinColumn(name = "game_id")
  protected Game game;

  protected Hero(String sign) {
    super(sign);
    this.isUsedThisTurn = false;
  }
}
