package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;

/**
 *
 */
public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {
    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris) && !c.getTournee().estCalculee()) {
        c.etatCreerRequete2.entryAction(intersection);
        c.setCurrentState(c.etatCreerRequete2);
        w.afficherMessage("Intersection sélectionnée pour la livraison : "
                                  + intersection.toString()
                                  + "\nSélectionnez un livreur");
      } else if (coord.equals(coordonneesSouris) && c.getTournee().estCalculee()) {
        c.etatCreerRequete3.entryAction(intersection);
        c.setCurrentState(c.etatCreerRequete3);
        w.afficherMessage("Intersection sélectionnée pour la livraison : "
                                  + intersection.toString()
                                  + "\nSélectionnez la livraison après laquelle vous voulez l'insérer car vous avez déjà calculé la tournée");
      }
    }
  }

  /**
   * Reset le state du controlleur au previousState
   * 
   * @param c
   * @param w
   */
  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    c.setCurrentState(c.previousState);
    w.allow(true);
  }

}
