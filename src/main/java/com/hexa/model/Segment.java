package com.hexa.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "destinationId", "longueur", "nom", "origineId" })
public class Segment {

  private double longueur;
  private String nom;
  private Intersection origine;
  private Intersection destination;
  private String origineId;
  private String destinationId;

  public Segment() {
  }

  public Segment(Intersection origine, Intersection destination, double longueur, String nom, String origineId,
      String destinationId) {
    this.origine = origine;
    this.destination = destination;
    this.longueur = longueur;
    this.nom = nom;
    this.destinationId = destinationId;
    this.origineId = origineId;
  }

  @XmlAttribute(name = "length")
  public double getLongueur() {
    return longueur;
  }

  public void setLongueur(double longueur) {
    this.longueur = longueur;
  }

  @XmlAttribute(name = "name")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  @XmlTransient
  public Intersection getOrigine() {
    return origine;
  }

  @XmlAttribute(name = "origin")
  public String getOrigineId() {
    return origineId;
  }

  public void setOrigineId(String origineId) {
    this.origineId = origineId;
  }

  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }

  public void setOrigine(Intersection origine) {
    this.origine = origine;
  }

  @XmlTransient
  public Intersection getDestination() {
    return destination;
  }

  @XmlAttribute(name = "destination")
  public String getDestinationId() {
    return destinationId;
  }

  public void setDestination(Intersection destination) {
    this.destination = destination;
  }

}
