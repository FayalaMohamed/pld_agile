package com.hexa.controller;

import com.hexa.view.Window;
import com.hexa.controller.EtatCarteChargee;
import com.hexa.controller.EtatAuMoinsUneRequete;

public class EtatCreerRequete2 implements State {

  public void clicDroit(Controller c, Window w) {
    c.setCurrentState(c.etatCarteChargee);
  }

  public void clicGauche(Controller c, Window w) {
    c.setCurrentState(c.etatAuMoinsUneRequete);
  }
}
