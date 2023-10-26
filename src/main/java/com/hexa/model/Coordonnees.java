package com.hexa.model;

public class Coordonnees {

  private int x;
  private int y;

  public Coordonnees(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean equals(Coordonnees autresCoordonnees) {
    int floue = 5;
    return (this.x <= autresCoordonnees.x + floue && this.x >= autresCoordonnees.x - floue
        && this.y <= autresCoordonnees.y + floue && this.y >= autresCoordonnees.y - floue);
  }

}
