package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.CircuitCommande;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.view.Window;

/**
 * Etat correspondant au cas où une carte est chargée et au moins une requête
 * existe
 * --> creerRequete ajoute une nouvelle requête à la liste de requêtes
 * --> supprimerRequete supprimer une requête de la liste de requêtes
 * --> chargerCarte rentre dans l'état EtatChargerCarte
 * --> sauvegarderRequete rentre dans l'état EtatSauvegarderRequetes
 * --> calculerTournee génère le circuit optimal pour la tournée en cours
 */
public class EtatAuMoinsUneRequete implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("Cliquez sur une intersection pour créer la requête");
    c.switchToState(c.getEtatCreerRequete1());
    c.setPreviousState(c.getEtatAuMoinsUneRequete());
  }

  public void chargerRequetes(Controller c, Window w) {
    c.switchToState(c.getEtatChargerRequete());
    c.entryAction();
  }

  public void supprimerRequete(Controller c, Window w) {
    if (c.getTournee().getLivraisons().length == 0) {
      c.switchToState(c.getEtatCarteChargee());
      return;
    }
    c.switchToState(c.getEtatSupprimerRequete());
  }

  public void chargerCarte(Controller c, Window w) {
    c.switchToState(c.getChargerCarte());
    c.setPreviousState(c.getEtatAuMoinsUneRequete());
    c.getChargerCarte().entryAction(c, w);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
    c.switchToState(c.getEtatSauvegarderRequete());
    c.entryAction();
  }

  public void calculerTournee(Controller c, Window w, ListOfCommands listOfCdes) {
    try {
      c.getTournee().construireCircuit(c.getCarte());
      w.afficherMessage("Tournée calculée");
      listOfCdes.add(new CircuitCommande(c.getTournee(), c.getTournee().getCircuit()));
    } catch (Exception e) {
      e.printStackTrace();
      w.afficherMessage(e.getMessage());
    }
  }

  @Override
  public void undo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.undo();
    if (c.getTournee().getLivraisons().length == 0) {
      c.switchToState(c.getEtatCarteChargee());
    }

  }

  @Override
  public void redo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.redo();
    if (c.getTournee().getLivraisons().length == 0) {
      c.switchToState(c.getEtatCarteChargee());
    }
  }
}
