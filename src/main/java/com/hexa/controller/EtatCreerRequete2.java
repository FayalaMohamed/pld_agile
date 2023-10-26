package com.hexa.controller;

import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Livreur;
import com.hexa.model.Tournee;
import com.hexa.view.Window;

public class EtatCreerRequete2 implements State {

  private Livraison livraison;

  public void entryAction(Intersection i) {
    livraison = new Livraison(i);
  }

  public void choixLivreur(Controller c, Window w, int livreur) {
    if (this.livraison == null) {
      w.afficherMessage("Vous devez choisir une intersection avant de choisir un livreur");
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
    tournee.ajouterLivraison(livraison);
    w.afficherMessage("Le livreur " + livreur + " a été affecté à la livraison : " + livraison);
    System.out.println("Livraisons de la tournee : ");
    for (Livraison livraison : tournee.getLivraisons()) {
      System.out.println(livraison);
    }
    c.setCurrentState(c.etatAuMoinsUneRequete);
  }

  public void clicDroit(Controller c, Window w) {
    w.afficherMessage("Création de requête annulée");
    c.setCurrentState(c.etatCarteChargee);
  }

  public void clicGauche(Controller c, Window w) {
    c.setCurrentState(c.etatAuMoinsUneRequete);
  }
}
