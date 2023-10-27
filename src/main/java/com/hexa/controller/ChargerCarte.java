package com.hexa.controller;

import java.io.File;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

/**
 * Etat de l'application après un clic sur le bouton "Charger une carte"
 * --> entryAction initialise la carte du controller
 */
public class ChargerCarte implements State {

  public void entryAction(Controller c, Window w) {
    // faire un truc comme : XMLParser.serlectfile() -> problème du singleton
    try {
      File xmlFile = XMLfileOpener.getInstance().open(true);
      if (xmlFile == null) {
          // TODO (itération 2) gérer aussi le cas où on charge d'un autre état que
          // l'initial
          c.setCurrentState(c.initialState);
      } else {
          Graphe map = XMLParser.xmlToGraphe(xmlFile.getAbsolutePath());
          w.afficherCarte(map);
          c.setCarte(map);
          c.setCurrentState(c.etatCarteChargee);
      }
    } catch (Exception e) {
      c.setCurrentState(c.initialState);
    }
  }
}
