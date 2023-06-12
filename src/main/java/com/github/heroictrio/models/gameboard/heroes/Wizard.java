package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Wizard extends Hero {

  @Column(name = "last_value")
  private short lastValue;

  public Wizard() {
    super(Constants.WIZARD_SIGN);
    this.lastValue = Short.MAX_VALUE;
  }
}
