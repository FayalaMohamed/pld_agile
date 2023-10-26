package com.hexa.controller;

import com.hexa.view.Window;

public class InitialState implements State {

  public void chargerCarte(Controller c, Window w) {
    c.setCurrentState(c.chargerCarte);
    c.chargerCarte.entryAction(c, w);
  }
}
