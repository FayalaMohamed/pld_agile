package com.hexa.view;

import com.hexa.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

  private Controller controller;

  public ButtonListener(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case Window.CHARGER_CARTE:
        controller.chargerCarte();
        break;
      case Window.CHARGER_REQUETES:
        controller.chargerRequetes();
        break;
      case Window.SAUVEGARDER_REQUETES:
        controller.sauvegarderRequetes();
        break;
    }
  }
}
