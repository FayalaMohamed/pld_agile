package com.hexa.controller;

import com.hexa.controller.EtatCreerRequete1;
import com.hexa.view.Window;

public class EtatCarteChargee implements State {

  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("création d'une nouvelle requête, veuillez sélectionner une intersection");
    c.setCurrentState(c.etatCreerRequete1);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
  }
}
