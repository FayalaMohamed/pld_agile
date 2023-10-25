package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.controller.EtatCarteChargee;
import com.hexa.view.Window;

public class InitialState implements State {

  public void chargerCarte(Controller c, Window w) {
    Graphe carte = null;
    try {
      carte = XMLParser.xmlToGraphe(null);
      // TODO implémenter la fonctionnalité d'annuler ICI
      c.setCarte(carte);
      w.afficherCarte(carte);
      c.setCurrentState(c.etatCarteChargee);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
