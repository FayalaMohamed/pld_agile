package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.view.Window;

public interface State {

  public default void clicGauche(Coordonnees Coordonnees) {
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

  public default void supprimerRequetes(Controller c, Window w) {
  }

  public default void sauvegarderRequetes(Controller c, Window w) {
  }

}
