package com.hexa.controller;

import com.hexa.view.Window;


/**
 * Etat initial de l'application à son démarrage.
 */
public class InitialState implements State {

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.chargerCarte);
    c.chargerCarte.entryAction(c, w);
  }
}
