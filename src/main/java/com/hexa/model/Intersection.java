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

  /**
   * @return double
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * @return double
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Une intersection est caractérisée par son id, sa longitude et sa latitude
   * 
   * @param id
   * @param longitude
   * @param latitude
   */
  public Intersection(Long id, double longitude, double latitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  /**
   * @return Long
   */
  public Long getId() {
    return id;
  }

  /**
   * Retourne le hash de l'id de l'intersection
   * 
   * @return int
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * Retourne True si l'Intersection et l'Object donné en paramètre sont égaux :
   * égaux si l'objet est de la classe Intersection et a le même id que
   * l'Intersection appelante
   * 
   * @param obj
   * @return boolean
   */
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

  /**
   * Retourne une description textuelle correspondant à l'Intersection
   * 
   * @return String
   */
  public String toString() {
    return ("id : " + id + " latitude : " + latitude + " longitude : " + longitude);
  }

  public String toStringNomSegments(Graphe g) {
    String returnString = "";
    Segment[] segments = g.getSegmentsFromIntersection(this);

    returnString += segments[0].getNom();
    for (int i = 1; i < segments.length; i++) {
      boolean found = false;
      for (int j = 0; j < i; j++) {
        // Eviter les doublons
        if (segments[i].getNom().strip().equals(segments[j].getNom().strip())) {
          found = true;
          break;
        }
      }
      if (!found) {
        returnString += ", ";
        returnString += (segments[i].getNom());
      }
    }

    return returnString + ".";
  }

  /**
   * Retourne le tag XML correspondant à l'Intersection
   * 
   * @return String
   */
  public String toTag() {
    return "<intersection id=\"" + id + "\" latitude=\"" + latitude + "\" longitude=\"" + longitude + "\"/>";
  }

}
