package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.view.Window;

public interface State {

  public default void clicGauche(Controller c, Window w, Coordonnees Coordonnees) {
  }

  public default void clicDroit(Controller c, Window w) {
  }

  // TODO modifier la signature de la fonction pour enelever file
  public default void chargerCarte(Controller c, Window w) {
  }

  public default void creerRequete(Controller c, Window w) {
  }

  // TODO modifier la signature de la fonction pour enelever file
  public default void chargerRequetes(Controller c, Window w, String file) {
  }

  public default void supprimerRequete(Controller c, Window w) {
  }

  public default void sauvegarderRequetes(Controller c, Window w) {
  }

  public default void calculerTournee(Controller c, Window w) {
  }

  public default void choixLivreur(Controller c, Window w, int livreur) {
  }

}
