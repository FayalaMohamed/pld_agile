package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ChargementRequetesCommande;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Livraison;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;
import java.io.File;
import java.util.Set;

import static com.hexa.model.XMLParser.xmlToListeLivraison;

/**
 * Etat dans lequel se trouve l'application quand le chargement d'un ensemble de
 * requêtes est en cours
 * --> entryAction charge un fichier de requêtes dans le controller
 */
public class EtatChargerRequete implements State {
  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance("requete").open(true);

      if (xmlFile == null) {
        if (c.getTournee().getLivraisons().length == 0)
          c.setCurrentState(c.getEtatCarteChargee());
        else
          c.setCurrentState(c.getEtatAuMoinsUneRequete());
      } else {
        // TODO c.getTournee().setCircuitCalculer(true);
        Set<Livraison> livraisons = xmlToListeLivraison(xmlFile.getAbsolutePath());
        c.getTournee().setLivraisons(livraisons);
        c.getListOfCommands().add(new ChargementRequetesCommande(c.getTournee(),livraisons));
        if (c.getTournee().getNbLivraisons() == 0) {
          c.setCurrentState(c.getEtatCarteChargee());
        } else {
          c.setCurrentState(c.getEtatAuMoinsUneRequete());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (c.getTournee().getNbLivraisons() == 0) {
        c.setCurrentState(c.getEtatCarteChargee());
      } else {
        c.setCurrentState(c.getEtatAuMoinsUneRequete());
      }
    }
    w.allow(true);
  }
}
