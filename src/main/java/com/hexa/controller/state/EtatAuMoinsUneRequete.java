package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.CircuitCommande;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Tournee;
import com.hexa.model.Livraison;
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
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        c.switchToState(c.getEtatSupprimerRequete());
        return;
      }
    }
    c.switchToState(c.getEtatCarteChargee());
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
      for (Tournee tournee : c.getTournees()) {
        System.out.println("TOURNEE  :  ");
        for (Livraison livraison : tournee.getLivraisons()) {
          System.out.println(livraison);
        }
        tournee.construireCircuit(c.getCarte());
        w.afficherMessage("Tournée calculée");
        listOfCdes.add(new CircuitCommande(tournee, tournee.getCircuit()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      w.afficherMessage(e.getMessage());
    }
  }

  @Override
  public void undo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.undo();
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        return;
      }
      c.switchToState(c.getEtatCarteChargee());
    }

  }

  @Override
  public void redo(ListOfCommands listOfCdes, Controller c) {
    listOfCdes.redo();
    for (Tournee tournee : c.getTournees()) {
      if (tournee.getLivraisons().length != 0) {
        return;
      }
      c.switchToState(c.getEtatCarteChargee());
    }
  }
}
