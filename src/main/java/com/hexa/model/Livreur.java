package com.hexa.model;


import java.util.*;

/**
 * 
 */
public class Livreur {

  /**
   *
   */
  private int id;

  /**
   *
   */
  private Set<Tournee> tournees;

  /**
   * Default constructor
   */
  public Livreur() {
  }

  public Livreur(int id) {
    this.id = id;
  }

  
  /** 
   * @return int
   */
  public int getId() {
    return id;
  }


  /**
   *
   * @return les tournées
   */
  public Set<Tournee> getTournees() {
    return tournees;
  }

  
  /** Ajouter une Tournee au livreur
   * @param tournee
   */
  public void addTournee(Tournee tournee) {
    this.tournees.add(tournee);
  }

  
  /** 
   * @return String
   */
  public String toString() {
    return "id : " + id;
  }
}