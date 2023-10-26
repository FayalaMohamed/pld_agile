package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;

public class EtatAuMoinsUneRequete implements State {

  public void creerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatCreerRequete1);
  }

  public void chargerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatChargerRequete);
    c.entryAction();
    // Graphe carte = null;
    // try {
    // carte = XMLParser.xmlToGraphe(null);
    // // TODO implémenter la fonctionnalité d'annuler ICI
    // c.setCarte(carte);
    // w.afficherCarte(carte);
    // c.setCurrentState(c.etatCarteChargee);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // w.afficherMessage("Opération impossible : fichier invalide");
    // }
  }

  public void supprimerRequetes(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
  }
}
