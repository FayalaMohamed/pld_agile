package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.view.Window;
import com.hexa.model.Tournee;

/**
 * Etat dans lequel une carte est chargée mais aucune requête n'existe
 * --> creerRequete rentre dans l'état etatCreerRequete1
 * --> chargerRequetes rentre dans l'état etatChargerRequetes
 * --> chargerCarte rentre dans l'état etatChargerCarte
 */
public class EtatCarteChargee implements State {

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * 
   * @param w
   */
  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Créer une
   * requête"
   * 
   * @param c
   * @param w
   */
  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("Cliquez sur une intersection pour créer la requête - Clic droit pour annuler");
    c.switchToState(c.getEtatCreerRequete1());
    c.setPreviousState(c.getEtatCarteChargee());
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger
   * des requêtes"
   * 
   * @param c
   * @param w
   */
  public void chargerRequetes(Controller c, Window w) {
    c.switchToState(c.getEtatChargerRequete());
    c.entryAction();
  }

  /**
   * Méthode appelée par le contrôleur après avoir cliqué sur le bouton "Charger
   * une carte"
   * 
   * @param c
   * @param w
   */
  public void chargerCarte(Controller c, Window w) {
    c.switchToState(c.getChargerCarte());
    c.setPreviousState(c.getEtatCarteChargee());
    c.entryAction();
  }

  /**
   * Méthode appelée par le controlleur quand on appuie sur le bouton undo
   * 
   * @param l listOfCommands
   * @param c controller
   */
  public void undo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.undo();
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        c.switchToState(c.getEtatAuMoinsUneRequete());
        break;
      }
    }
  }

  /**
   * Méthode appelée par le controlleur quand on appuie sur le bouton redo
   * 
   * @param l listOfCommands
   * @param c controller
   */
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
