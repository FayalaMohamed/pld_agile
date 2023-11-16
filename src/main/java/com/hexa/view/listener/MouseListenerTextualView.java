package com.hexa.view.listener;

import com.hexa.controller.Controller;
import com.hexa.model.Coordonnees;
import com.hexa.model.Livraison;
import com.hexa.model.Tournee;
import com.hexa.model.TourneeException;
import com.hexa.view.GraphicalView;
import com.hexa.view.Window;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListenerTextualView extends MouseAdapter {

//-------------------------------------------------------------------------------------------------

    private Controller controller;

    private GraphicalView graphicalView;
    private Window window;

    private Coordonnees dernieresCoordonnees;

//-------------------------------------------------------------------------------------------------

    public MouseListenerTextualView(Controller c, Livraison l) {
        this.controller = c;
    }

//-------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée par le MouseListener chaque fois qu'un clic de souris est
     * effectué
     *
     * @param evt the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                try {
                    //controller.clicGauche(coordinates(evt));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case MouseEvent.BUTTON3:
                System.out.println("Right click pressed");
                controller.clicDroit();
                break;
            default:
        }
    }


}

