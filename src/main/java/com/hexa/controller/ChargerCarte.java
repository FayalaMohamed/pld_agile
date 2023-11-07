package com.hexa.controller;

import java.io.File;

import com.hexa.model.Graphe;
import com.hexa.model.Segment;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

/**
 * Etat de l'application après un clic sur le bouton "Charger une carte"
 * --> entryAction initialise la carte du controller
 */
public class ChargerCarte implements State {

  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance("map").open(true);
      if (xmlFile == null) {
        c.setCurrentState(c.previousState);
      } else {
        Graphe map = XMLParser.xmlToGraphe(xmlFile.getAbsolutePath());

        w.afficherCarte(map);
        w.afficherMessage("");
        c.setCarte(map);
        c.setCurrentState(c.etatCarteChargee);
      }
    } catch (Exception e) {
      w.afficherMessage("Le fichier sélectionné est invalide - veuillez réessayer");
      c.setCurrentState(c.previousState);
    }
    w.allow(true);
  }
}
