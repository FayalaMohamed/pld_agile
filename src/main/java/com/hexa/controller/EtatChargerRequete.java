package com.hexa.controller;

import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

/**
 * Etat dans lequel se trouve l'application quand le chargement d'un ensemble de
 * requêtes est en cours
 * --> entryAction charge un fichier de requêtes dans le controller
 */
public class EtatChargerRequete implements State {
  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance().open(true);

      if (xmlFile == null) {
        c.setCurrentState(c.etatCarteChargee);
      } else {
        // TODO c.getTournee().setCircuitCalculer(true);
        c.getTournee().setLivraisons(xmlToListeLivraison(xmlFile.getAbsolutePath()));
        if (c.getTournee().getNbLivraisons() == 0) {
          c.setCurrentState(c.etatCarteChargee);
        } else {
          c.setCurrentState(c.etatAuMoinsUneRequete);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (c.getTournee().getNbLivraisons() == 0) {
        c.setCurrentState(c.etatCarteChargee);
      } else {
        c.setCurrentState(c.etatAuMoinsUneRequete);
      }
    }
    w.allow(true);
  }
}
