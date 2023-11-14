package com.hexa.controller.state;

import java.io.File;

import com.hexa.controller.Controller;
import com.hexa.model.Graphe;
import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

/**
 * Etat de l'application après un clic sur le bouton "Charger une carte" -->
 * entryAction initialise la carte du controller
 */
public class ChargerCarte implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance("map").open(true);
      if (xmlFile == null) {
        c.switchToState(c.getPreviousState());
      } else {
        Graphe map = XMLParser.xmlToGraphe(xmlFile.getAbsolutePath());

        w.afficherCarte(map);
        w.afficherMessage("");
        c.setCarte(map);
        c.switchToState(c.getEtatCarteChargee());
        c.initTournees();
      }
    } catch (Exception e) {
      w.afficherMessage("Le fichier sélectionné est invalide - veuillez réessayer");
      c.switchToState(c.getPreviousState());
    }
  }
}
