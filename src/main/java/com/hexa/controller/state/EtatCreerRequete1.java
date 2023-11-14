package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;
import java.util.ArrayList;
import java.util.List;

public class EtatCreerRequete1 implements State {

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands l) {
    List<Intersection> intersectionsSelectionnees = new ArrayList<>();

    for (Intersection intersection : c.getCarte().getIntersections()) {
      Coordonnees coord = w.getGraphicalView().CoordGPSToViewPos(intersection);
      if (coord.equals(coordonneesSouris)) {
        intersectionsSelectionnees.add(intersection);
      }
    }

    Intersection intersectionChoisie = null;
    if (intersectionsSelectionnees.isEmpty()) {
      w.afficherMessage("Veuillez cliquer sur une intersections valide.");
    } else if (intersectionsSelectionnees.size() == 1) {
      intersectionChoisie = intersectionsSelectionnees.get(0);
    } else {
      intersectionChoisie = w.popupChoixIntersections(intersectionsSelectionnees);
      if (intersectionChoisie == null) {
        w.afficherMessage("Vous n'avez pas choisi d'intersection.");
        return;
      }
    }
    w.afficherMessage("Intersection sélectionnée pour la livraison : " + intersectionChoisie.toString()
        + "\nSélectionnez un livreur");
    c.setCurrentState(c.getEtatCreerRequete2());
    c.getEtatCreerRequete2().entryAction(intersectionChoisie);

    // if (!c.getTournee().estCalculee()) {
    // w.afficherMessage("Intersection sélectionnée pour la livraison : " +
    // intersectionChoisie.toString()
    // + "\nSélectionnez un livreur");
    // c.setCurrentState(c.getEtatCreerRequete2());
    // c.getEtatCreerRequete2().entryAction(intersectionChoisie);
    // } else if (c.getTournee().estCalculee()) {
    // w.afficherMessage("Intersection sélectionnée pour la livraison : " +
    // intersectionChoisie.toString()
    // + "\nSélectionnez la livraison après laquelle vous voulez l'insérer car vous
    // avez déjà calculé la tournée");
    // c.getEtatCreerRequete3().entryAction(intersectionChoisie);
    // c.setCurrentState(c.getEtatCreerRequete3());
    // }

  }

  /**
   * Reset le state du controlleur au previousState
   * 
   * @param c
   * @param w
   */
  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    c.setCurrentState(c.getPreviousState());
    w.allow(true);
  }

}
