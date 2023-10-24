package com.hexa.model;

import java.util.*;

/**
 * 
 */
public class Entrepot extends Intersection {

  /**
   * @param id
   * @param longitude
   * @param latitude
   */
  public Entrepot(Long id, double longitude, double latitude) {
    super(id, longitude, latitude);
  }

  public String toTag() {
    return "<warehouse address=\"" + id + "\"/>";
  }
}
