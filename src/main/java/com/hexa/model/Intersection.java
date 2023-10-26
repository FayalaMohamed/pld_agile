package com.hexa.model;

import java.util.*;

/**
 * 
 */
public class Intersection {

  /**
   * latitude réel fourni par le fichier XML
   */
  private double latitude;

  /**
   * longitude réel fourni par le fichier XML
   */
  private double longitude;

  /**
   * identifient
   */
  protected Long id;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  /**
   * @param id
   * @param longitude
   * @param latitude
   */
  public Intersection(Long id, double longitude, double latitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Intersection other = (Intersection) obj;
    return id.equals(other.id);
  }

  public String toString() {
    return ("id : " + id + " latitude : " + latitude + " longitude : " + longitude);
  }

  public String toTag() {
    return "<intersection id=\"" + id + "\" latitude=\"" + latitude + "\" longitude=\"" + longitude + "\"/>";
  }

}
