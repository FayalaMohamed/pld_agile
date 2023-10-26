package com.hexa.controller;

import com.hexa.view.Window;

public interface State {

  public default void entryAction(Controller c, Window w) {

  }

  public default void clicGauche() {
  }

  public default void clicDroit() {
  }

  public default void chargerCarte(Controller c, Window w) {
  }

  public default void creerRequete(Controller c, Window w) {
  }

  public default void chargerRequetes(Controller c, Window w) {
  }

  public default void supprimerRequetes(Controller c, Window w) {
  }

  public default void sauvegarderRequetes(Controller c, Window w) {
  }

}
