package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

public class EtatChargerRequete implements State {
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
        c.setCurrentState(c.etatCarteChargee);
      }
    } catch (Exception e) {
      c.setCurrentState(c.initialState);
    }
  }
}
