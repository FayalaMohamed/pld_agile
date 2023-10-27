package com.hexa.controller;

import com.hexa.view.Window;

/**
 * Etat dans lequel une carte est chargée mais aucune requête n'existe
 * --> creerRequete rentre dans l'état etatCreerRequete1
 * --> chargerRequetes rentre dans l'état etatChargerRequetes
 * --> chargerCarte rentre dans l'état etatChargerCarte
 */
public class EtatCarteChargee implements State {

  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("Création d'une nouvelle requête, veuillez sélectionner une intersection.");
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
