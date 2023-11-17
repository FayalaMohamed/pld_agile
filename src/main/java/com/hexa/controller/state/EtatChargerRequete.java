package com.hexa.controller.state;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.hexa.controller.command.ChargementRequetesCommande;
import com.hexa.controller.Controller;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

import static com.hexa.model.XMLParser.*;

/**
 * Etat dans lequel se trouve l'application quand le chargement d'un ensemble de
 * requêtes est en cours --> entryAction charge un fichier de requêtes dans le
 * controller
 */
public class EtatChargerRequete implements State {

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * 
   * @param w
   */
  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * 
   * @param c
   * @param w
   */
  public void entryAction(Controller c, Window w) {
    try {
      File xmlFile = XMLfileOpener.getInstance("requete").open(true);

      if (xmlFile == null) {
        boolean auMoinsUneLivraison = false;
        for (Tournee tournee : c.getTournees()) {
          if (tournee.getLivraisons().length != 0) {
            auMoinsUneLivraison = true;
            break;
          }
        }
        if (auMoinsUneLivraison) {
          c.switchToState(c.getEtatAuMoinsUneRequete());
        } else {
          c.switchToState(c.getEtatCarteChargee());
        }
      } else {
        ArrayList<Tournee> tournees = xmlToListeLivraison2(xmlFile.getAbsolutePath());
        for (Tournee tournee : tournees) {
          ArrayList<Livraison> livraisons = new ArrayList<>(Arrays.asList(tournee.getLivraisons()));
          for (Livraison livraison : livraisons) {
            if (!c.getCarte().hasIntersection(livraison.getLieu())) {
              w.afficherMessage("La carte ne contient pas toutes les livraisons");
              for (Tournee tournee2 : c.getTournees()) {
                if (tournee2.getLivraisons().length != 0) {
                  c.switchToState(c.getEtatAuMoinsUneRequete());
                  return;
                }
              }
              c.switchToState(c.getEtatCarteChargee());
              return;
            }
          }
        }
        c.getListOfCommands().add(new ChargementRequetesCommande(c, tournees));
        c.switchToState(c.getEtatAuMoinsUneRequete());
      }
    } catch (Exception e) {
      e.printStackTrace();
      for (Tournee tournee : c.getTournees()) {
        if (tournee.getNbLivraisons() != 0) {
          c.switchToState(c.getEtatAuMoinsUneRequete());
          return;
        }
      }
      c.switchToState(c.getEtatCarteChargee());
    }
  }
}
