package com.hexa.controller;

import com.hexa.controller.EtatCreerRequete1;
import com.hexa.view.Window;

public class EtatCarteChargee implements State {

  public void creerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatCreerRequete1);
  }

  public void chargerRequetes(Controller c, Window w) {
    c.setCurrentState(c.etatChargerRequete);
    c.entryAction();
  }

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.chargerCarte);
    c.entryAction();
  }
}
