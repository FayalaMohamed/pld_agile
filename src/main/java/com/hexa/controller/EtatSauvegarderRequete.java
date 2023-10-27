package com.hexa.controller;

import com.hexa.model.XMLParser;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;

/**
 * Etat de l'application permettant la sauvegarde des requêtes
 * --> entryAction sauvegarde la liste de livraisons actuelle du contrôleur dans
 * le fichier choisi
 */
public class EtatSauvegarderRequete implements State {

  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance().open(false);
      if (xmlFile != null) {
        XMLParser.listeLivraisonsToXml(xmlFile.getAbsolutePath(), (c.getTournee().getLivraisonsSet()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
    w.allow(true);
  }
}
