package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.controller.EtatCarteChargee;
import com.hexa.view.Window;

public class InitialState implements State {

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.chargerCarte);
    c.chargerCarte.entryAction(c, w);
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
}
