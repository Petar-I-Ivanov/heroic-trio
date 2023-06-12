package com.github.heroictrio.models.gameboard.heroes;

import com.github.heroictrio.utilities.Constants;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Gnome extends Hero {

  public Gnome() {
    super(Constants.GNOME_SIGN);
  }
}
