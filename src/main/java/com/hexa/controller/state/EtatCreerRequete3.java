package com.hexa.controller.state;

import java.util.ArrayList;
import java.util.Arrays;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.view.Window;

public class EtatCreerRequete3 implements State {
  private Livraison livraison;
  private Tournee tournee;

  /**
   * Rentrer dans l'état EtatCreerRequete3 et donner en paramètre l'intersection à
   * ajouter à la tournée après calcul du plus court chemin
   * 
   * @param intersection
   */
  public void entryAction(Livraison livraison, Tournee tournee) {
    this.livraison = livraison;
    this.tournee = tournee;
  }

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  /**
   * Parcourt les intersections de la carte du controlleur et si ça correspond à
   * une intersection qui appartient à la tournée (ou à l'entrepôt) passage à
   * l'état EtatCreerRequete2
   * 
   * @param c
   * @param w
   * @param coordonneesSouris
   */
  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands l) {
    ArrayList<Intersection> intersectionsEtEntrepot = new ArrayList<>(
        Arrays.asList(c.getCarte().getIntersections()));
    Intersection entrepot = c.getCarte().getEntrepot();
    intersectionsEtEntrepot.add(entrepot);
    System.out.println("etat requete 3");
    for (Intersection intersection : intersectionsEtEntrepot) {
      // TODO: When doing graphical view, refactor the method to compute coordinates
      // not to duplicate code
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)
          && (tournee.estLieuLivraison(intersection) || entrepot.equals(intersection))) {
        try {
          tournee.ajouterLivraisonApresCalcul(c.getCarte(), livraison, new Livraison(intersection));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        c.switchToState(c.getEtatAuMoinsUneRequete());
        w.afficherMessage("Livraison Insérée !");
        w.allow(true);
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
    c.switchToState(c.getPreviousState());
  }
}
