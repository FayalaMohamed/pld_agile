package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;

public class EtatAuMoinsUneRequete implements State {

  public void creerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatCreerRequete1);
  }

  public void chargerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatChargerRequete);
    c.entryAction();
  }

  public void supprimerRequetes(Controller c, Window w) {
    // TODO decider de la manière de supprimer
    c.setCurrentState(c.etatCarteChargee);
  }

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.chargerCarte);
    c.entryAction();
  }

  public void sauvegarderRequetes(Controller c, Window w) {
    c.setCurrentState(c.etatSauvegarderRequete);
    c.entryAction();
  }
}
