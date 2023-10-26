package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;

public class EtatAuMoinsUneRequete implements State {

  public void creerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatCreerRequete1);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
  }

  public void supprimerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatSupprimerRequete);
  }

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
  }
}
