package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;

/**
 *
 */
public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {
    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        // TODO: Break when first intersection found, because there could be multiple
        // intersections for one click
        w.afficherMessage("Intersection sélectionnée pour la livraison");
        c.etatCreerRequete2.entryAction(intersection);
        c.setCurrentState(c.etatCreerRequete2);
        w.afficherMessage("Sélectionnez un livreur");
      }
    }
  }

  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    System.out.println("Cancelled the creation of new request");
    c.setCurrentState(c.etatCarteChargee);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
  }
}
