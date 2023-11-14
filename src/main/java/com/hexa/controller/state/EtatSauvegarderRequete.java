package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.model.Tournee;
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
      File xmlFile = XMLfileOpener.getInstance("requete").open(false);
      if (xmlFile != null) {
        for (Tournee tournee : c.getTournees()) {
          XMLParser.listeLivraisonsToXml(xmlFile.getAbsolutePath(), (tournee.getLivraisons()));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      c.setCurrentState(c.getEtatAuMoinsUneRequete());
    }
    w.allow(true);
  }
}
