package com.hexa.controller.state;

import java.util.ArrayList;

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

  public void entryAction(Intersection intersection) {
    livraison = new Livraison(intersection);
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
      c.setCurrentState(c.getEtatCreerRequete3());
      c.getEtatCreerRequete3().entryAction(livraison, tournee);
    } else if (tournee != null) {
      tournee.ajouterLivraison(livraison);
      c.setCurrentState(c.getEtatAuMoinsUneRequete());
      w.afficherMessage("Le livreur " + livreur + " a été affecté à la livraison : " + livraison);
      w.allow(true);
      listOfCommands.add(new RequeteCommande(tournee, livraison));
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
    c.setCurrentState(c.getPreviousState());
    w.allow(true);
  }
}
