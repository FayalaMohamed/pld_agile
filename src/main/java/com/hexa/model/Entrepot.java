package com.hexa.model;


/**
 * 
 */
public class Entrepot extends Intersection {

  /** Un Entrepot hérite d'une Intersection
   * @param id
   * @param longitude
   * @param latitude
   */
  public Entrepot(Long id, double longitude, double latitude) {
    super(id, longitude, latitude);
  }

  
  /** Retourne le tag XML correspondant à cet Entrepot
   * @return String
   */
  public String toTag() {
    return "<warehouse address=\"" + id + "\"/>";
  }
}
