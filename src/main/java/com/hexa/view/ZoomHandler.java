package com.hexa.view;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.hexa.controller.Controller;

public class ZoomHandler implements MouseWheelListener {

    private Controller controller;
    private GraphicalView graphicalView;

  public ZoomHandler(Controller c, GraphicalView gv) {
    this.controller = c;
    this.graphicalView = gv;
  }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      int notches = e.getWheelRotation();
      graphicalView.setZoomFactor(notches);
      controller.zoom();


    }
  }