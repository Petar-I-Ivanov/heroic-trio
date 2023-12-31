package com.github.heroictrio.models.gameboard;

import com.github.heroictrio.utilities.Position;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class GameboardObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(nullable = false)
  protected String sign;

  @Column(name = "row_location", nullable = false)
  protected byte rowLocation;

  @Column(name = "col_location", nullable = false)
  protected byte colLocation;

  protected GameboardObject(String sign) {
    this.sign = sign;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position location) {
    this.rowLocation = location.getRow();
    this.colLocation = location.getCol();
  }
}
