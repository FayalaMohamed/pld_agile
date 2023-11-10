package com.hexa.model;

import java.util.*;

/**
 * 
 */
public class Segment {

  /**
   * Longueur en mètres du segment de route
   */
  private double longueur;

  /**
   * Nom du segment
   */
  private String nom;

  /**
   * point de départ
   */
  private Intersection origine;

  /**
   * point d'arrivée
   */
  private Intersection destination;

  /**
   * @param origine
   * @param destination
   * @param longueur
   * @param nom
   */
  public Segment(Intersection origine, Intersection destination, double longueur, String nom) {
    this.destination = destination;
    this.origine = origine;
    this.nom = nom;
    this.longueur = longueur;
  }

  /**
   * @param origine
   * @param destination
   */
  public Segment(Intersection origine, Intersection destination) {
    this.destination = destination;
    this.origine = origine;
    this.nom = null;
    this.longueur = Double.MAX_VALUE;
  }

  /**
   * @return double
   */
  public double getLongueur() {
    return longueur;
  }

  /**
   * @return Intersection
   */
  public Intersection getOrigine() {
    return origine;
  }

  /**
   * @return Intersection
   */
  public Intersection getDestination() {
    return destination;
  }

  /**
   * @return String
   */
  public String getNom() {
    return nom;
  }

  /**
   * Retourn le hash du Segment (à partir de l'origine et la destination)
   * 
   * @return int
   */
  @Override
  public int hashCode() {
    return Objects.hash(destination, origine);
  }

  /**
   * Retourne True si le Segment et l'Object donné en paramètre sont égaux :
   * égaux si l'objet est de la classe Segment et a la même origine et destination
   * que
   * le Segment appelant
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
    Segment other = (Segment) obj;
    return Objects.equals(destination, other.destination) && Objects.equals(origine, other.origine);
  }

  /**
   * Retourne le tag XML correspondant au Segment
   * 
   * @return String
   */
  public String toTag() {
    return "<segment destination=\"" + destination.getId() + "\" length=\"" + longueur + "\" name=\"" + nom
        + "\" origin=\"" + origine.getId() + "\"/>";
  }

}
