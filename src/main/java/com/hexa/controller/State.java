package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.view.Window;

public interface State {

  public default void clicGauche() {
  }

  public default void clicDroit() {
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
