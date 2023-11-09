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
    w.allow(false);
    w.afficherMessage("Cliquez sur une intersection pour créer la requête");
    c.setCurrentState(c.etatCreerRequete1);
    c.setPreviousState(c.etatCarteChargee);
  }

  public void chargerRequetes(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.etatChargerRequete);
    c.entryAction();
  }

  public void chargerCarte(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.chargerCarte);
    c.setPreviousState(c.etatCarteChargee);
    c.entryAction();
  }

  public void undo(ListOfCommands listOfCdes,Controller c){
    listOfCdes.undo();
    if (c.getTournee().getLivraisons().length != 0) {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
  }

  public void redo(ListOfCommands listOfCdes,Controller c){
    listOfCdes.redo();
    if (c.getTournee().getLivraisons().length != 0) {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
  }
}
