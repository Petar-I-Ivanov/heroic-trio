package com.github.heroictrio.models;

import com.github.heroictrio.models.gameboard.heroes.Dwarf;
import com.github.heroictrio.models.gameboard.heroes.Gnome;
import com.github.heroictrio.models.gameboard.heroes.Wizard;
import com.github.heroictrio.models.gameboard.terrain.Background;
import com.github.heroictrio.models.gameboard.terrain.Boss;
import com.github.heroictrio.models.gameboard.terrain.EmptyCorner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private short turn;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GameStatusEnum status;

  @OneToOne(mappedBy = "game")
  private Gnome gnome;

  @OneToOne(mappedBy = "game")
  private Dwarf dwarf;

  @OneToOne(mappedBy = "game")
  private Wizard wizard;

  @OneToMany(mappedBy = "game")
  private List<Background> backgrounds;

  @OneToMany(mappedBy = "game")
  private List<Boss> boss;

  @OneToOne(mappedBy = "game")
  private EmptyCorner emptyCorner;

  public Game() {
    this.turn = 1;
    this.status = GameStatusEnum.ONGOING;
  }
}
