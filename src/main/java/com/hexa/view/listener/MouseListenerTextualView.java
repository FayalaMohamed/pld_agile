package com.hexa.view.listener;

import com.hexa.controller.Controller;
import com.hexa.model.Coordonnees;
import com.hexa.model.Livraison;
import com.hexa.view.GraphicalView;
import com.hexa.view.Window;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListenerTextualView extends MouseAdapter {

//-------------------------------------------------------------------------------------------------

    private Controller controller;
    private Livraison livraison;

//-------------------------------------------------------------------------------------------------

    public MouseListenerTextualView(Controller c, Livraison l) {
        System.out.println("création mouse Listener");
        this.controller = c;
        this.livraison = l;
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
                    System.out.println("clic gauche sur livraison vue texte");
                    controller.clicGauche(livraison);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case MouseEvent.BUTTON3:
                controller.clicDroit();
                break;
            default:
        }
    }


}

