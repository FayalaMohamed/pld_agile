package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;
import com.hexa.controller.EtatCreerRequete2;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;

public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonnees) {
    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
    }
  }

  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Cancelled the creation of new request");
    System.out.println("Cancelled the creation of new request");
    c.setCurrentState(c.etatCarteChargee);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
  }
}
