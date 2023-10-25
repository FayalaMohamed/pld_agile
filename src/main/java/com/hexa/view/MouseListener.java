package com.hexa.view;

import java.awt.event.MouseEvent;

import com.hexa.controller.Controller;

public class MouseListener implements java.awt.event.MouseListener {

  private Controller controller;

  public MouseListener(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'mouseClicked'");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        controller.clicGauche();
      case MouseEvent.BUTTON2:
        controller.clicDroit();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'mouseReleased'");
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'mouseEntered'");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'mouseExited'");
  }
}
