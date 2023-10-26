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

  public int getId() {
    return id;
  }

  public Set<Tournee> getTournees() {
    return tournees;
  }

  public void addTournee(Tournee tournee) {
    this.tournees.add(tournee);
  }

  public String toString() {
    return "id : " + id;
  }
}