package com.hexa.controller;

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

  public void creerRequete(Controller c, Window w) {
    w.allow(false);
    w.afficherMessage("Cliquez sur une intersection pour créer la requête");
    c.setCurrentState(c.etatCreerRequete1);
    c.setPreviousState(c.etatAuMoinsUneRequete);
  }
  
  public void chargerRequetes(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.etatChargerRequete);
    c.entryAction();
  }

  public void supprimerRequete(Controller c, Window w) {
    w.allow(false);
    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
      return;
    }
    c.setCurrentState(c.etatSupprimerRequete);
  }

  public void chargerCarte(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.chargerCarte);
    c.setPreviousState(c.etatAuMoinsUneRequete);
    c.chargerCarte.entryAction(c, w);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.etatSauvegarderRequete);
    c.entryAction();
  }

  public void calculerTournee(Controller c, Window w, ListOfCommands listOfCdes) {
    try {
      c.getTournee().construireCircuit(c.getCarte());
      // w.getGraphicalView().paintComponent(w.getGraphics());
      w.getGraphicalView().repaint();
      listOfCdes.add(new CircuitCommande(c.getTournee(),c.getTournee().getCircuit()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undo(ListOfCommands listOfCdes,Controller c){
    listOfCdes.undo();
    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
    }

  }

  @Override
  public void redo(ListOfCommands listOfCdes,Controller c){
    listOfCdes.redo();
    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
    }
  }
}
