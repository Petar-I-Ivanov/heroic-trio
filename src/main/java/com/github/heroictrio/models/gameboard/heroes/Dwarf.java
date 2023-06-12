package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Dwarf extends Hero {

  @Column(name = "last_value")
  private short lastValue;

  public Dwarf() {
    super(Constants.DWARF_SIGN);
    this.lastValue = 0;
  }
}
