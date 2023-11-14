package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.view.Window;

/**
 * Etat initial de l'application à son démarrage.
 */
public class InitialState implements State {

  public void chargerCarte(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.getChargerCarte());
    c.setPreviousState(c.getInitialState());
    c.getChargerCarte().entryAction(c, w);
  }


}
