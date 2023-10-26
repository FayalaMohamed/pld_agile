package com.hexa.controller;

import com.hexa.model.Intersection;
import com.hexa.model.Livraison;
import com.hexa.model.Livreur;
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
    livraison.setLivreur(new Livreur(livreur));
    w.afficherMessage("Le livreur " + livreur + " a été affecté à la livraison : " + livraison);
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
