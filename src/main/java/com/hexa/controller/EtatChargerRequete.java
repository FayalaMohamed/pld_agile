package com.hexa.controller;

import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

public class EtatChargerRequete implements State {
  public void entryAction(Controller c, Window w) {
    // faire un truc comme : XMLParser.serlectfile() -> problème du singleton
    try {
      File xmlFile = XMLfileOpener.getInstance().open(true);

      if (xmlFile == null) {
        c.setCurrentState(c.etatCarteChargee);
      } else {
        // TODO ajouter le chargement des requetes
        c.getTournee().setLivraisons(xmlToListeLivraison(xmlFile.getAbsolutePath()));
        c.setCurrentState(c.etatAuMoinsUneRequete);
      }
    } catch (Exception e) {
      e.printStackTrace();
      c.setCurrentState(c.initialState);
    }
  }
}
