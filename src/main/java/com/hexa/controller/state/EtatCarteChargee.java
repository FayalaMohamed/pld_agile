package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.view.Window;

/**
 * Etat dans lequel une carte est chargée mais aucune requête n'existe 
 * --> creerRequete rentre dans l'état etatCreerRequete1 
 * --> chargerRequetes rentre dans l'état etatChargerRequetes 
 * --> chargerCarte rentre dans l'état etatChargerCarte
 */
public class EtatCarteChargee implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("Cliquez sur une intersection pour créer la requête - Clic droit pour annuler");
    c.switchToState(c.getEtatCreerRequete1());
    c.setPreviousState(c.getEtatCarteChargee());
  }

  public void chargerRequetes(Controller c, Window w) {
    c.switchToState(c.getEtatChargerRequete());
    c.entryAction();
  }

  public void chargerCarte(Controller c, Window w) {
    c.switchToState(c.getChargerCarte());
    c.setPreviousState(c.getEtatCarteChargee());
    c.entryAction();
  }

  public void undo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.undo();
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
      c.switchToState(c.getEtatAuMoinsUneRequete());
	break;
	}
    }
  }

  public void redo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.redo();
        for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
      	c.switchToState(c.getEtatAuMoinsUneRequete());
	break;
	}
    }
  }
}
