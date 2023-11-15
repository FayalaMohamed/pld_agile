package com.hexa.controller.state;

import com.hexa.controller.Controller;
import com.hexa.controller.command.ListOfCommands;
import com.hexa.controller.command.RequeteCommande;
import com.hexa.model.GrapheException;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Livreur;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.view.Window;
import java.util.ArrayList;

/**
 * Etat de l'application quand on se trouve dans la deuxième étape de la
 * création d'une requête
 * --> entryAction initialise la livraison avec l'intersection choisie à l'étape
 * 1
 * --> choixLivreur complète la nouvelle livraison en lui assignant un livreur
 * --> clicDroit annule la création de requête et revient à etatCarteChargee
 */
public class EtatCreerRequete2 implements State {

  private Livraison livraison;
  private Livraison livraisonPrecedente = null;

  public void entryAction(Window w) {
    w.hideButtons(this);
  }

  public void entryAction(Intersection intersection) {
    livraison = new Livraison(intersection);
  }

  public void entryAction(Intersection intersectionAjouter, Intersection intersectionPrecedente) {
    livraison = new Livraison(intersectionAjouter);
    livraisonPrecedente = new Livraison(intersectionPrecedente);
  }

  public void choixLivreur(Controller c, Window w, int livreur, ListOfCommands listOfCommands)
      throws TourneeException, GrapheException {

    if (this.livraison == null) {
      w.afficherMessage("Attention - Vous devez choisir une intersection avant de choisir un livreur");
      return;
    }

    ArrayList<Tournee> tournees = c.getTournees();
    boolean livreurFound = false;
    Tournee tournee = null;
    for (Tournee tour : tournees) {
      if (tour != null && tour.getLivreur() != null && tour.getLivreur().getId() == livreur) {
        livreurFound = true;
        tournee = tour;
        break;
      }
    }

    if (!livreurFound) {
      Livreur liv = new Livreur(livreur);
      tournee = new Tournee();
      tournee.setLivreur(liv);
      c.addTournee(tournee);
    }

    if (tournee != null && tournee.estCalculee()) {
      c.switchToState(c.getEtatCreerRequete3());
      c.getEtatCreerRequete3().entryAction(livraison, tournee);
      w.afficherMessage("Selectionnez la livraison après laquelle la nouvelle livraison sera insérée");
    } else if (tournee != null) {
      System.out.println("State - choix livreur");
      tournee.ajouterLivraison(livraison);
      w.afficherMessage("Le livreur " + livreur + " a été affecté à la livraison : " + livraison);
      listOfCommands.add(new RequeteCommande(tournee, livraison));
      c.switchToState(c.getEtatAuMoinsUneRequete());
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
