package com.github.heroictrio.dto;

import com.github.heroictrio.models.GameStatusEnum;
import lombok.Data;

@Data
public class GameDTO {

  private long id;
  private short turn;
  private GameStatusEnum status;
  private String[][] gameboard;
}
