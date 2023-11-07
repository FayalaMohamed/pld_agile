package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;
import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {
    List<Intersection> intersections = new ArrayList<>();

    // Parcours de toutes les intersections
    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        intersections.add(intersection);
      }

    }
    // Si aucune intersection ne convient
    if (intersections.isEmpty()) {
      w.afficherMessage("Veuillez cliquer sur une intersections valide");
    } else {
      // S'il y a une seule et unique intersection
      if (intersections.size() == 1) {
        w.afficherMessage("Intersection sélectionnée pour la livraison");
        c.etatCreerRequete2.entryAction(intersections.get(0));
        c.setCurrentState(c.etatCreerRequete2);
        w.afficherMessage("Sélectionnez un livreur");
      } else {
        w.afficherMessage("Intersection sélectionnée pour la livraison");
        c.etatCreerRequete2.entryAction(w.popupChoixIntersections(intersections));
        c.setCurrentState(c.etatCreerRequete2);
        w.afficherMessage("Sélectionnez un livreur");
      }
    }
  }

  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    System.out.println("Cancelled the creation of new request");
    c.setCurrentState(c.previousState);
    w.allow(true);
  }

}
