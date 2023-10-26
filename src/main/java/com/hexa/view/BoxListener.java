package com.hexa.view;

import com.hexa.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoxListener implements ActionListener {

  private Controller controller;

  public BoxListener(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      default:
        System.out.println("COMBO BOX");
        break;
    }
  }
}
