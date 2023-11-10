package com.hexa.controller;

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
  private Livraison livraisonPrecedente = null;

  public void entryAction(Intersection intersection) {
    livraison = new Livraison(intersection);
  }

  public void entryAction(Intersection intersectionAjouter, Intersection intersectionPrecedente) {
    livraison = new Livraison(intersectionAjouter);
    livraisonPrecedente = new Livraison(intersectionPrecedente);
  }

  public void choixLivreur(Controller c, Window w, int livreur) throws TourneeException, GrapheException {
    if (this.livraison == null) {
      w.afficherMessage("Attention - Vous devez choisir une intersection avant de choisir un livreur");
      return;
    }
    // FIX: This is not good, need to have a list of livreurs somewhere and perform
    // a getLivreurById (create the livreur and append it to the list if it does not
    // exist yet)
    Tournee tournee = c.getTournee();
    Livreur livreur_obj = new Livreur(livreur);
    if (tournee.getLivreur() == null) {
      tournee.setLivreur(livreur_obj);
    }
    if (tournee.getLivreur().getId() != livreur) {
      w.afficherMessage(
          "Le livreur demandé n'est pas le livreur affecté à la tournée, on est dans l'itération 1 monsieur, une seule tournée !");
      return;
    }
    livraison.setLivreur(livreur_obj);
    if (c.getTournee().estCalculee()) {
      tournee.ajouterLivraisonApresCalcul(c.getCarte(),livraison,livraisonPrecedente);
    } else {
      tournee.ajouterLivraison(livraison);      
    }
    w.afficherMessage("Le livreur " + livreur + " a été affecté à la livraison : " + livraison);
    System.out.println("Livraisons de la tournee : ");
    for (Livraison livraison : tournee.getLivraisons()) {
      System.out.println(livraison);
    }
    c.setCurrentState(c.etatAuMoinsUneRequete);
    w.allow(true);
  }

  /**
   * Reset le state du controlleur au previousState
   * 
   * @param c
   * @param w
   */
  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    c.setCurrentState(c.previousState);
    w.allow(true);
  }
}
