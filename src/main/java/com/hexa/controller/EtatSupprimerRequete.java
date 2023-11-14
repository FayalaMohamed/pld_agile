package com.hexa.controller;

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
    w.afficherMessage("Annulation de la suppression de requête");
    if (c.getTournee().getNbLivraisons() == 0) {
      c.setCurrentState(c.etatCarteChargee);
    } else {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
    w.allow(true);
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) {

    Intersection intersectionSelectionnee = w.getIntersectionSelectionnee(coordonneesSouris);

    if (intersectionSelectionnee != null) {
      c.getTournee().supprimerLivraison(intersectionSelectionnee);
      w.afficherMessage("Intersection supprimée : "
                                  + intersectionSelectionnee.toString());
    }

    if (c.getTournee().getLivraisons().length == 0) {
      c.setCurrentState(c.etatCarteChargee);
    } else {
      c.setCurrentState(c.etatAuMoinsUneRequete);
    }
    
    w.allow(true);
  }
  
}
