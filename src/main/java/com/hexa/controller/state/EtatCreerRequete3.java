package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.AjoutRequeteApresCircuit;
import com.hexa.model.*;
import com.hexa.view.Window;

/**
 * Etat de l'application quand on se trouve dans la troisième étape de la
 * création d'une requête correspondant à la sélection de la livraison après
 * laquelle insérer une nouvelle livraison dans le cas où la tournée est déjà
 * calculée
 * --> clickGauche sur une livraison pour choisir la livraison précédant la
 * nouvelle livraison
 * --> clicDroit annule la création de requête et revient à etatCarteChargee
 */
public class EtatCreerRequete3 implements State {
  private Livraison livraison;
  private Tournee tournee;

  /**
   * Rentrer dans l'état EtatCreerRequete3 et donner en paramètre la livraison à
   * ajouter à la tournée après calcul du plus court chemin
   * 
   * @param livraison
   * @param tournee
   */
  public void entryAction(Livraison livraison, Tournee tournee) {
    this.livraison = livraison;
    this.tournee = tournee;
  }

  /**
   * Méthode appelée par le contrôleur après être entré dans un nouvel Etat
   * 
   * @param c
   * @param w
   */
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
   * @throws TourneeException
   */
  public void clicGauche(Controller c, Window w, Coordonnees coordonneesSouris) throws TourneeException {

    Intersection intersection = w.getIntersectionsSelectionnees(coordonneesSouris).get(0);

    if (tournee.estLieuLivraison(intersection) || intersection.equals(c.getCarte().getEntrepot())) {
      c.getListOfCommands().add(new AjoutRequeteApresCircuit(tournee, c.getCarte(), livraison, intersection));

      c.switchToState(c.getEtatAuMoinsUneRequete());
      w.afficherMessage("Livraison Insérée !");
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
