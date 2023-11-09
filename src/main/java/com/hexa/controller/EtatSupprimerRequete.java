package com.hexa.controller;

import com.hexa.model.Livraison;
import com.hexa.view.Window;
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

  public void clicDroit(Controller c, Window w) {
    System.out.println("Annuler Supprimer Requête");
    if (c.getTournee().getLivraisonsSet().isEmpty()) {
      c.setCurrentState(c.etatCarteChargee);
    } else {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
    w.allow(true);
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands listOfCommands) {

    for (Intersection intersection : c.getCarte().getIntersections()) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        Livraison livraison = c.getTournee().chercherLivraison(intersection);
        c.getTournee().supprimerLivraison(intersection);
        listOfCommands.add(new SuppresionRequeteCommande(c.getTournee(), livraison ));

      }
    }

    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
    } else {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
    
    w.allow(true);

  }
  
}
