package com.hexa.controller.state;

import com.hexa.model.Livraison;
import com.hexa.view.Window;
import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.controller.command.SuppresionRequeteCommande;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;

/**
 * Etat de l'application permettant de supprimer des requêtes
 * --> clicDroit permet de retourner dans le mode etatCarteChargee ou
 * etatAuMoinsUneRequete
 * --> clicGauche permet de supprimer la livraison correspondant à
 * l'intersection cliquée par l'utilisateur
 */
public class EtatSupprimerRequete implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void clicDroit(Controller c, Window w) {
    if (c.getTournee().getNbLivraisons() == 0) {
      c.switchToState(c.getEtatCarteChargee());
    } else {
      c.switchToState(c.getEtatAuMoinsUneRequete());
    }
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands listOfCommands) {

    for (Intersection intersection : c.getCarte().getIntersections()) {
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        Livraison livraison = c.getTournee().getLivraison(intersection);
        c.getTournee().supprimerLivraison(intersection);
        listOfCommands.add(new SuppresionRequeteCommande(c.getTournee(), livraison));

      }
    }

    if (c.getTournee().getLivraisons().length == 0) {
      c.switchToState(c.getEtatCarteChargee());
    } else {
      c.switchToState(c.getEtatAuMoinsUneRequete());
    }
  }

}
