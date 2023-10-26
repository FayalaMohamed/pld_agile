package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.model.Intersection;
import com.hexa.model.Livraison;

public class EtatCreerRequete2 implements State {

  private Livraison livraison;

  public void entryAction(Intersection i) {
    livraison = new Livraison(i);
  }

  public void clicDroit(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void clicGauche(Controller c, Window w) {
    c.setCurrentState(c.etatAuMoinsUneRequete);
  }
}
