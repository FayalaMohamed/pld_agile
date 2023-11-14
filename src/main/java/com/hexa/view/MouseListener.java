package com.hexa.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import com.hexa.controller.Controller;
import com.hexa.model.Coordonnees;

public class MouseListener extends MouseAdapter implements MouseMotionListener {

  private Controller controller;
  private GraphicalView graphicalView;
  private Window window;
  private Coordonnees dernieresCoordonnees;

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

  /**
   * Méthode appelée par le MouseListener chaque fois qu'un clic de souris est effectué
   * @param evt the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent evt) {
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


  @Override
  public void mousePressed(MouseEvent evt) {
    dernieresCoordonnees = coordinates(evt);

  }

  @Override
  public void mouseDragged(MouseEvent evt) {

    graphicalView.setDrag(coordinates(evt),this.dernieresCoordonnees);
    controller.glissement();
    dernieresCoordonnees = coordinates(evt);
    
}

}
