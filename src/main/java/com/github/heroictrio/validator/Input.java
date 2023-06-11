package com.github.heroictrio.validator;

import lombok.Data;

@Data
@ValidInput
public class Input {

  private String heroPick;
  private String direction;
}
