package com.hexa.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.hexa.controller.Controller;
import com.hexa.model.Coordonnees;

public class MouseListener extends MouseAdapter {

  private Controller controller;
  private GraphicalView graphicalView;
  private Window window;

  public MouseListener(Controller c, GraphicalView gv, Window w) {
    this.controller = c;
    this.graphicalView = gv;
    this.window = w;
  }

  private Coordonnees coordinates(MouseEvent evt) {
    MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
    int x = Math.round((float) e.getX());
    int y = Math.round((float) e.getY());
    return new Coordonnees(x, y);
  }

  @Override
  public void mouseClicked(MouseEvent evt) {
    // Method called by the mouse listener each time the mouse is clicked
    switch (evt.getButton()) {
      case MouseEvent.BUTTON1:
        controller.clicGauche(coordinates(evt));
        break;
      case MouseEvent.BUTTON3:
        System.out.println("Right click pressed");
        controller.clicDroit();
        break;
      default:
    }
  }

  public void mouseMoved(MouseEvent evt) {
  }

}
