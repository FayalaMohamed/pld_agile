package com.hexa.view;

import java.awt.Color;
import java.awt.Graphics;

import com.hexa.model.Entrepot;

public class VueEntrepot {

    private Entrepot entrepot;

    private Color color = Color.GREEN;
    private int rayon = 6;

    private int xpos;
    private int ypos;

    public VueEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
    
    public void dessinerVue(GraphicalView gv) {

        xpos = (int) ((entrepot.getLongitude() - gv.getLongitudeMin()) / (gv.getLongitudeMax() - gv.getLongitudeMin()) * gv.getViewWidth());
        ypos = (int) (gv.getViewHeight() - ((entrepot.getLatitude() - gv.getLatitudeMin()) / (gv.getLatitudeMax() - gv.getLatitudeMin()) * gv.getViewHeight()));

        gv.getGraphics().setColor(color);
        gv.getGraphics().fillOval(xpos - rayon, ypos - rayon, 2 * rayon, 2 * rayon);
    }
}
