package com.hexa.controller;

import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;

/**
 *
 */
public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {

    Intersection intersectionSelectionnee = w.getIntersectionSelectionnee(coordonneesSouris);

    if (intersectionSelectionnee != null) {
      c.etatCreerRequete2.entryAction(intersectionSelectionnee);
      c.setCurrentState(c.etatCreerRequete2);
      w.afficherMessage("Intersection sélectionnée pour la livraison : "
                                  + intersectionSelectionnee.toString()
                                  + "\nSélectionnez un livreur");
    }
  }

  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    System.out.println("Cancelled the creation of new request");
    c.setCurrentState(c.previousState);
    w.allow(true);
  }

}
