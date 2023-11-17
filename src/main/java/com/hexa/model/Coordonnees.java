package com.hexa.model;

/**
 * Classe modélisant des coordonnées de la vue graphique.
 */
public class Coordonnees {
	
//-------------------------------------------------------------------------------------------------------------

	private int x;
	private int y;

//-------------------------------------------------------------------------------------------------------------
	
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
//-------------------------------------------------------------------------------------------------------------

  /** 
   * @return x
   */
  public int getX() {
    return x;
  }

  /** 
   * @return y
   */
  public int getY() {
    return y;
  }
  
  /** Retourne True si les deux paires de Coordonnées sont égales en comparant les x et y
   * @param autresCoordonnees
   * @return boolean
   */
  public boolean equals(Coordonnees autresCoordonnees) {
    int flou = 5;
    return (this.x <= autresCoordonnees.x + flou && this.x >= autresCoordonnees.x - flou
        && this.y <= autresCoordonnees.y + flou && this.y >= autresCoordonnees.y - flou);
  }

  public String toString() {
    return "xpos=" + x + " / ypos=" + y;
  }
}
