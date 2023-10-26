package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;

public class EtatSupprimerRequete implements State {
  
  public void clicDroit(Controller c, Window w) {
    System.out.println("Annuler Supprimer Requête");
    c.setCurrentState(c.etatAuMoinsUneRequete);
  }
  
  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {

    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        c.getTournee().supprimerLivraison(intersection);
      }
    }

    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
    } else {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
  }
}
