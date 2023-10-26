package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;
import com.hexa.model.GrapheException;
import com.hexa.model.TourneeException;

public class EtatAuMoinsUneRequete implements State {

  public void creerRequete(Controller c, Window w) {
    w.afficherMessage("création d'une nouvelle requête, veuillez sélectionner une intersection");
    c.setCurrentState(c.etatCreerRequete1);
  }

  // TODO modifier la signature de la fonction pour enelever file
  public void chargerRequetes(Controller c, Window w, String file) {
  }

  public void supprimerRequete(Controller c, Window w) {
    c.setCurrentState(c.etatSupprimerRequete);
  }

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void sauvegarderRequetes(Controller c, Window w) {
  }

  public void calculerTournee(Controller c, Window w) {
    try {
      c.getTournee().construireCircuit(c.getCarte());
    } catch (Exception e) {
      //TODO
      e.printStackTrace();
    }
  }
}
