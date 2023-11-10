package com.hexa.controller;

import com.hexa.model.Intersection;
import com.hexa.view.Window;
import com.hexa.model.Coordonnees;
import com.hexa.model.Livraison;

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

  public void calculerTournee(Controller c, Window w) {
    try {
      c.getTournee().construireCircuit(c.getCarte());
      w.afficherMessage("Tournée calculée");
      w.getGraphicalView().repaint();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {
    for (Livraison livraison : c.getTournee().getLivraisons() {
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(livraison.getLieu());
      if (coord.equals(coordonneesSouris)) {
        w.getTextualView().highlight(livraison);
      }
    }

  }
}
