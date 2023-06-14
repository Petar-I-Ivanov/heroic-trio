package com.github.heroictrio.dto;

import com.github.heroictrio.models.GameStatusEnum;
import lombok.Data;

@Data
public class GameDTO {

  private String id;
  private short turn;
  private GameStatusEnum status;

  private boolean isGnomeUsed;
  private boolean isDwarfUsed;
  private boolean isWizardUsed;

  private String[][] gameboard;
}
