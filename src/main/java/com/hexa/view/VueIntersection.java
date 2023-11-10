package com.hexa.view;

import java.awt.Color;

import com.hexa.model.Intersection;
import com.hexa.observer.Observable;

public class VueIntersection extends Observable {

    private Intersection intersection;

    private Color color = Color.BLUE;
    private int rayon = 6;
    private int xpos;
    private int ypos;

    public VueIntersection(Intersection i){
        this.intersection = i;
    }

    public void dessinerVue(GraphicalView gv) {

        xpos = (int) ((intersection.getLongitude() - gv.getLongitudeMin()) / (gv.getLongitudeMax() - gv.getLongitudeMin()) * gv.getViewWidth());
        ypos = (int) (gv.getViewHeight() - ((intersection.getLatitude() - gv.getLatitudeMin()) / (gv.getLatitudeMax() - gv.getLatitudeMin()) * gv.getViewHeight()));

        gv.getGraphics().setColor(color);
        gv.getGraphics().fillOval(xpos - rayon, ypos - rayon, 2 * rayon, 2 * rayon);
    }
}
