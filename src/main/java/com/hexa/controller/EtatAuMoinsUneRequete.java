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
    w.afficherMessage("création d'une nouvelle requête, veuillez sélectionner une intersection");
    c.setCurrentState(c.etatCreerRequete1);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
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
    c.chargerCarte.entryAction(c, w);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
    w.allow(false);
    c.setCurrentState(c.etatSauvegarderRequete);
    c.entryAction();
  }

  public void calculerTournee(Controller c, Window w) {
    try {
      c.getTournee().construireCircuit(c.getCarte());
      // w.getGraphicalView().paintComponent(w.getGraphics());
      w.getGraphicalView().repaint();
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
    }
  }
}
