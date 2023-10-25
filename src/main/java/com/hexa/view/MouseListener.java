package com.hexa.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.hexa.controller.Controller;

public class MouseListener extends MouseAdapter {

	private Controller controller;
	private GraphicalView graphicalView;
	private Window window;

	public MouseListener(Controller c, GraphicalView gv, Window w){
		this.controller = c;
		this.graphicalView = gv;
		this.window = w;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// Method called by the mouse listener each time the mouse is clicked 
		switch (evt.getButton()){
		case MouseEvent.BUTTON1:
				controller.clicGauche(); 
			  break;
		case MouseEvent.BUTTON3: 
			controller.clicGauche(); 
			break;
		default:
		}
	}

	public void mouseMoved(MouseEvent evt) {
	}
}