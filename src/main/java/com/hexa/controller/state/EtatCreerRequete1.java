package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.model.Coordonnees;
import com.hexa.model.Intersection;
import com.hexa.view.Window;
import java.util.ArrayList;
import java.util.List;

public class EtatCreerRequete1 implements State {

  public void entryAction(Window w) {
    w.hideButtons(this);
    w.afficherMessage("Cliquez sur une intersectionp pour la sélectionner");
  }

  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris, ListOfCommands l) {
    
    List<Intersection> intersectionsSelectionnees = w.getIntersectionSelectionnee(coordonneesSouris);

    Intersection intersectionChoisie = null;
    if (intersectionsSelectionnees.isEmpty()) {
      w.afficherMessage("Veuillez cliquer sur une intersections valide");
    } else if (intersectionsSelectionnees.size() == 1) {
      intersectionChoisie = intersectionsSelectionnees.get(0);
    } else {
      intersectionChoisie = w.popupChoixIntersections(intersectionsSelectionnees);
      if (intersectionChoisie == null) {
        w.afficherMessage("Vous n'avez pas choisi d'intersection.");
        c.switchToState(c.getPreviousState());
        return;
      }
    }

    w.afficherIntersectionSelectionnee(intersectionChoisie);
    w.afficherMessage("Intersection sélectionnée pour la livraison - Sélectionnez un livreur");
    c.switchToState(c.getEtatCreerRequete2());
    c.getEtatCreerRequete2().entryAction(intersectionChoisie);
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
